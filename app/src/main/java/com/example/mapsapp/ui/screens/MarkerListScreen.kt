package com.example.mapsapp.ui.screens

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

@Composable
fun MarkerListScreen( contentPadding: PaddingValues){
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
            Modifier
                .fillMaxWidth()
                .weight(0.6f)
        ) {
            items(markList) { student ->
                val dissmissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            myViewModel.deleteMarcador(student.id!!)
                            true
                        } else {
                            false
                        }
                    }
                )
                SwipeToDismissBox(state = dissmissState, backgroundContent = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }) {
                    StudentItem(student)
                }
            }
        }
    }
}

@Composable
fun StudentItem(student: Marcador) {
    Box(
        modifier = Modifier
            .fillMaxWidth().background(Color.LightGray).border(width = 2.dp, Color.DarkGray)
            ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(student.title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = "Mark: ${student.descripcion}",  color = Color.White)
        }
    }
}