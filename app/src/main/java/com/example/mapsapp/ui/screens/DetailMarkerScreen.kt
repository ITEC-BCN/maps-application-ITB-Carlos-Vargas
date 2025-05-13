package com.example.mapsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun DatailsMarkScreen(marcador: Marcador){
    Card(modifier = Modifier
        .fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {

        Image(painter = rememberAsyncImagePainter(marcador.imagen), contentDescription = marcador.descripcion,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Row( verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth())
        {

            Text(
                text = marcador.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón al final

            IconButton(onClick = {
                //myViewModel.accionFavorito(personaje!!)
            }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorito"
                    //imageVector = if (isFav) Icons.Default.Clear
                    //else Icons.Default.Favorite,
                    //contentDescription = "Favorito"

                )
            }

        }
        Text(
            text = "Debut en: ${marcador.descripcion}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}