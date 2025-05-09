package com.example.mapsapp.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.R
import com.example.mapsapp.data.Marcador
import com.example.mapsapp.viewmodels.CreateMarkViewModel

import java.io.File

@Composable
fun CreateMarkerScreen(contentPadding: PaddingValues, altitud: Double, longitud: Double){
    val myViewModel = viewModel<CreateMarkViewModel>()
    Column(Modifier.fillMaxSize().padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Box(Modifier.fillMaxSize().weight(60f), contentAlignment = Alignment.Center){
            EditForm(myViewModel, altitud, longitud)
        }
    }

}
@Composable
fun EditForm(createMarkerViewModel: CreateMarkViewModel, altitud: Double, longitud: Double) {
    val title: String? by createMarkerViewModel.acltualTitle.observeAsState("")
    val descripcion: String? by createMarkerViewModel.actualDescripcion.observeAsState("")





    //funcionamiento de seleccion de la imagen
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

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

    var showDialog by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = title!!, label = { Text("Tituo") },
            onValueChange = { createMarkerViewModel.editTitle(it) })
        TextField(
            value = descripcion!!, label = { Text("Descripcion") },
            onValueChange = { createMarkerViewModel.editDescripcion(it) })
        //boton para agregar la imagen
        Button(onClick = { showDialog = true }){
            Image(
                painter = painterResource(id = R.drawable.imagensymbol),
                contentDescription = "Example",modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(16.dp)),
                alpha = 1f
            )
        }
        Button(onClick = {

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