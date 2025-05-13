package com.example.mapsapp.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.R
import com.example.mapsapp.data.Marcador
import com.example.mapsapp.viewmodels.CreateMarkViewModel
import com.example.mapsapp.viewmodels.SupabaseViewModel
import com.google.android.gms.maps.model.LatLng

import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateMarkerScreen(contentPadding: PaddingValues, altitud: Double, longitud: Double,  navigateToNext: (PaddingValues) -> Unit){
    Column(Modifier.fillMaxSize().padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Box(Modifier.fillMaxSize().weight(60f), contentAlignment = Alignment.Center){
            val myViewModel = viewModel<SupabaseViewModel>()
            val title: String? by myViewModel.marcadorTitle.observeAsState("")
            val descripcion: String? by myViewModel.marcadorDescripcion.observeAsState("")

            //funcionamiento de seleccion de la imagen
            val context = LocalContext.current
            val imageUri = remember { mutableStateOf<Uri?>(null) }
            val bitmap = remember { mutableStateOf<Bitmap?>(null) }
            var showDialog by remember { mutableStateOf(false) }

            val imageModifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { showDialog = true } // Aquí el evento del clic


            val takePictureLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                    if (success && imageUri.value != null) {
                        val stream = context.contentResolver.openInputStream(imageUri.value!!)
                        bitmap.value = BitmapFactory.decodeStream(stream)
                    }
                }

            val pickImageLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                    uri?.let {
                        imageUri.value = it
                        val stream = context.contentResolver.openInputStream(it)
                        bitmap.value = BitmapFactory.decodeStream(stream)
                    }
                }

            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                TextField(
                    value = title!!, label = { Text("Tituo") },
                    onValueChange = { myViewModel.editMarcadorTitle(it) })
                TextField(
                    value = descripcion!!, label = { Text("Descripcion") },
                    onValueChange = { myViewModel.editMarcadorDescripcion(it) })
                //boton para agregar la imagen
                if (bitmap.value == null) {
                    Image(
                        painter = painterResource(id = R.drawable.imagensymbol),
                        contentDescription = "Example",
                        modifier = imageModifier,
                        alpha = 1f
                    )
                } else {
                    bitmap.value?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            modifier = imageModifier,
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Button(onClick = {
                    myViewModel.insertNewMarcador(title!!,altitud, longitud, descripcion!!, bitmap.value!!)
                    navigateToNext(contentPadding)
                }) { Text("Guardar canvis") }
            }

            if (showDialog) {

                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Selecciona una opción") },
                    text = { Text("¿Quieres tomar una foto o elegir una desde la galería?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            val uri = createImageUri(context)
                            imageUri.value = uri
                            takePictureLauncher.launch(uri!!)
                        }) {
                            Text("Tomar Foto")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                            pickImageLauncher.launch("image/*")
                        }) {
                            Text("Elegir de Galería")
                        }
                    }
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditForm( altitud: Double, longitud: Double) {}



fun createImageUri(context: Context): Uri? {
    val file = File.createTempFile("temp_image_", ".jpg", context.cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}