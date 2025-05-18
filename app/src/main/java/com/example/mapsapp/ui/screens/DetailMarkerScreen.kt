package com.example.mapsapp.ui.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.LaunchedEffect
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

@SuppressLint("InvalidColorHexValue")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsMarkScreen(
    contentPadding: PaddingValues,
    idMarcador: Int,
    navigateBack: () -> Unit
) {
    val vm: SupabaseViewModel = viewModel()
    val isLoading: Boolean by vm.showLoading.observeAsState(true)
    val original: Marcador? by vm.selectedMarcador.observeAsState()
    val titleState: String? by vm.marcadorTitle.observeAsState("")
    val descState: String? by vm.marcadorDescripcion.observeAsState("")
    val imgUrl: String? by vm.marcadorImagen.observeAsState("")
    val alt: Double? by vm.marcadorAltitud.observeAsState()
    val lon: Double? by vm.marcadorLongitud.observeAsState()
    val showDialog = vm.showAlert.value

    // Bitmap local al que el usuario cambie la imagen
    var newBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Cargamos el marcador la primera vez
    LaunchedEffect(idMarcador) {
        vm.getMarcador(idMarcador)
        newBitmap = null
    }

    // Detectamos si hay cambios (texto o imagen)
    val hasChanges = remember(titleState, descState, newBitmap) {
        original != null && (
                titleState  != original!!.title ||
                        descState   != original!!.descripcion ||
                        newBitmap   != null
                )
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else{
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            // Imagen editable
            ImagenBoton(
                markImagen = imgUrl.orEmpty(),
                onImageSelected = { bmp ->
                    newBitmap = bmp
                }
            )

            Spacer(Modifier.height(24.dp))
            TextField(
                value = "${alt ?: 0.0} m",
                onValueChange = {},
                label = { Text("Altitud") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp)
            )

            // Longitud como TextField solo lectura
            TextField(
                value = "${lon ?: 0.0}°",
                onValueChange = {},
                label = { Text("Longitud") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(Modifier.height(24.dp))

            // Título editable
            TextField(
                value = titleState.orEmpty(),
                onValueChange = { vm.editMarcadorTitle(it) },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Descripción editable
            TextField(
                value = descState.orEmpty(),
                onValueChange = { vm.editMarcadorDescripcion(it) },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(Modifier.weight(1f))

            // Botones al pie
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = navigateBack) {
                    Text("Volver")
                }
                Row {
                    if (hasChanges) {
                        Button(onClick = {
                            original?.id?.let { id ->
                                vm.updateMarcador(
                                    id,
                                    titleState!!,
                                    descState!!,
                                    newBitmap  // si es null, la imagen no cambia
                                )
                            }
                        }) {
                            Text("Guardar cambios")
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                    TextButton(onClick = { vm.updateShowAlert(true) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        Spacer(Modifier.width(4.dp))
                        Text("Eliminar")
                    }
                }
            }
        }
    }

    // Diálogo de confirmación de borrado
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { vm.updateShowAlert(false) },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Seguro que deseas eliminar este marcador?") },
            confirmButton = {
                TextButton(onClick = {
                    original?.let {
                        vm.deleteStudent(it.id!!, it.imagen)
                        vm.updateShowAlert(false)
                        navigateBack()
                    }
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { vm.updateShowAlert(false) }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// ----------------------------------
// Reusa tu composable de selección:
// ----------------------------------
@Composable
fun ImagenBoton(markImagen: String, onImageSelected: (Bitmap) -> Unit) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val takeLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && imageUri.value != null) {
                context.contentResolver.openInputStream(imageUri.value!!)?.use { stream ->
                    BitmapFactory.decodeStream(stream)?.also {
                        bitmap.value = it
                        onImageSelected(it)
                    }
                }
            }
        }

    val pickLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                context.contentResolver.openInputStream(it)?.use { stream ->
                    BitmapFactory.decodeStream(stream)?.also {
                        bitmap.value = it
                        onImageSelected(it)
                    }
                }
            }
        }

    Box(
        Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ) {
        if (bitmap.value != null) {
            Image(
                bitmap = bitmap.value!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = markImagen,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Selecciona opción") },
            text = { Text("Tomar foto o elegir de galería") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    imageUri.value = createImageUri(context)
                    takeLauncher.launch(imageUri.value!!)
                }) { Text("Tomar foto") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    pickLauncher.launch("image/*")
                }) { Text("Galería") }
            }
        )
    }
}