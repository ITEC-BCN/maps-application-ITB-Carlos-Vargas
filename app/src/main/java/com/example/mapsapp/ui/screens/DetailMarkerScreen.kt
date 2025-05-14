package com.example.mapsapp.ui.screens

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.mapsapp.data.Marcador
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.R
import com.example.mapsapp.viewmodels.SupabaseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsMarkScreen(contentPadding: PaddingValues, idMarcador: Int, navigateToNext: () -> Unit){

    val myViewModel = viewModel<SupabaseViewModel>()
    val showLoading: Boolean by myViewModel.showLoading.observeAsState(true)

    val title: String? by myViewModel.marcadorTitle.observeAsState("")
    val descripcion: String? by myViewModel.marcadorDescripcion.observeAsState("")
    val markImagen: String? by myViewModel.marcadorImagen.observeAsState("")

    val showDialog = myViewModel.showAlert.value
    val marcador: Marcador? by myViewModel.selectedMarcador.observeAsState()

    var imagenSeleccionada by remember { mutableStateOf<Bitmap?>(null) }

    var alertaCambio by remember { mutableStateOf<Boolean>(false) }

    myViewModel.getMarcador(idMarcador)

    if (showLoading){
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }else{
        //myViewModel.editMarcadorTitle(marcador!!.title)
        //myViewModel.editMarcadorDescripcion(marcador!!.descripcion)
        Card(modifier = Modifier
            .fillMaxSize().padding(contentPadding),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White))
        {


            ImagenBoton(
                markImagen = markImagen!!,
                onImageSelected = { bitmap ->
                    imagenSeleccionada = bitmap
                }
            )
            Row( verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth())
            {


                TextField(
                    value = title!!, label = { Text(marcador!!.title) },
                    onValueChange = {
                        myViewModel.editMarcadorTitle(it)
                        if (title != marcador!!.title){
                            alertaCambio = true
                        }
                        else{
                            alertaCambio = false
                        }
                    }
                )

                Spacer(modifier = Modifier.weight(1f)) // Empuja el botón al final

                IconButton(onClick = {
                    myViewModel.updateShowAlert(true)
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Favorito"
                    )
                }

            }
            TextField(
                value = descripcion!!, label = { Text(marcador!!.descripcion) },
                onValueChange = {
                    myViewModel.editMarcadorDescripcion(it)
                    if (descripcion != marcador!!.descripcion){
                        alertaCambio = true
                    }
                    else{
                        alertaCambio = false
                    }
                }
            )
            if (alertaCambio || imagenSeleccionada != null){
                Button(onClick = {
                    myViewModel.updateMarcador(marcador!!.id!!, title!!, descripcion!!, imagenSeleccionada )
                }) {
                    Text(text = "Guardar cambios")
                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {  myViewModel.updateShowAlert(false) },
                text = { Text("¿Desea eliminar este marcador?") },
                confirmButton = {
                    TextButton(onClick = {
                        myViewModel.deleteStudent(marcador!!.id!!, marcador!!.imagen)
                        myViewModel.updateShowAlert(false)
                        navigateToNext()
                    }) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        myViewModel.updateShowAlert(false)

                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun ImagenBoton(markImagen: String,  onImageSelected: (Bitmap) -> Unit){
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val imageModifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { showDialog = true } // Aquí el evento del clic

    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && imageUri.value != null) {
                val stream = context.contentResolver.openInputStream(imageUri.value!!)
                val bmp = BitmapFactory.decodeStream(stream)
                bitmap.value = bmp
                onImageSelected(bmp)

            }
        }

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri.value = it
                val stream = context.contentResolver.openInputStream(it)
                val bmp = BitmapFactory.decodeStream(stream)
                bitmap.value = bmp
                onImageSelected(bmp)
            }
        }
    if (bitmap.value == null) {
        Image(
            painter = rememberAsyncImagePainter(markImagen),
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