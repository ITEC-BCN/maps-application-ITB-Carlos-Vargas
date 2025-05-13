package com.example.mapsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.viewmodels.SupabaseViewModel

@Composable
fun DetailsMarkScreen(contentPadding: PaddingValues, idMarcador: Int, navigateToNext: () -> Unit){

    val myViewModel = viewModel<SupabaseViewModel>()
    val showLoading: Boolean by myViewModel.showLoading.observeAsState(true)
    myViewModel.getMarcador(idMarcador)
    val showDialog = myViewModel.showAlert.value
    val marcador: Marcador? by myViewModel.selectedMarcador.observeAsState()

    if (showLoading){
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }else{
        Card(modifier = Modifier
            .fillMaxSize().padding(contentPadding),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White))
        {

            Image(painter = rememberAsyncImagePainter(marcador!!.imagen), contentDescription = marcador!!.descripcion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Row( verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth())
            {

                Text(
                    text = marcador!!.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
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
            Text(
                text = "Debut en: ${marcador!!.descripcion}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
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