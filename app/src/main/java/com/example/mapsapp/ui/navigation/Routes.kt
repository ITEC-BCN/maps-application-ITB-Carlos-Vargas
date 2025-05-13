package com.example.mapsapp.ui.navigation

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

@Serializable
object Permisos

@Serializable
object  Details

@Serializable
data class CreateMark(val altitud : Double, val longitud:Double)

sealed class Destination {

    @Serializable
    object Map: Destination()

    @Serializable
    object List: Destination()

}
