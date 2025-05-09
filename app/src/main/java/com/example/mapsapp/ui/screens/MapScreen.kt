package com.example.mapsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.viewmodels.MapviewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(contentPadding: PaddingValues, navigateToNext: (Double,Double) -> Unit){
    val myViewModel = viewModel<MapviewModel>()
    var showDialog = myViewModel.showAlert.value

    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }

    Column(Modifier.fillMaxSize()) {
        val itb = LatLng(41.4534225, 2.1837151)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 17f)
        }
        GoogleMap(
            Modifier.fillMaxSize(), cameraPositionState = cameraPositionState,
            onMapClick = {
                Log.d("MAP CLICKED", it.toString())
            }, onMapLongClick = {LatLng ->
                latitude = LatLng.latitude
                longitude = LatLng.longitude
                myViewModel.updateShowAlert(true)
            }){
            Marker(
                state = MarkerState(position = itb), title = "ITB",
                snippet = "Marker at ITB")
        }

    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {  myViewModel.updateShowAlert(false) },
            text = { Text("¿Crear una marca en esta pocición?") },
            confirmButton = {
                TextButton(onClick = {
                    myViewModel.updateShowAlert(false)
                    navigateToNext(latitude!!,longitude!!)
                }) {
                    Text("Crear")
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
