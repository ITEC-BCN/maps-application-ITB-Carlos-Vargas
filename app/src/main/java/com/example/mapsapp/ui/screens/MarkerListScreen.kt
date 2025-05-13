package com.example.mapsapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.viewmodels.SupabaseViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mapsapp.data.Marcador
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter

@Composable
fun MarkerListScreen( contentPadding: PaddingValues, navigateToNext: (Int) -> Unit){
    val myViewModel = viewModel<SupabaseViewModel>()
    val markList by myViewModel.marcadorList.observeAsState(emptyList<Marcador>())
    myViewModel.getAllMarcadors()
    val markTitle: String by myViewModel.marcadorTitle.observeAsState("")
    val markDescription: String by myViewModel.marcadorDescripcion.observeAsState("")


    Column(
        Modifier.fillMaxSize().padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Text(
            "Students List",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(markList) { marcador ->
                    MarcadorItem(marcador, navigateToNext)
            }
        }
    }
}

@Composable
fun MarcadorItem(marcador: Marcador,  navigateToNext: (Int) -> Unit) {
    Card(
        border = BorderStroke(2.dp, Color.LightGray), shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),
        onClick = { navigateToNext(marcador.id!!)}
    ) {
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            Image(rememberAsyncImagePainter(marcador.imagen), contentDescription = marcador.title)
            Text(
                text = marcador.title,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize()
            )
        }
    }
}