package com.example.mapsapp.ui.navigation

import com.example.mapsapp.data.Marcador
import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

@Serializable
object Permisos

@Serializable
data class  Details(val idMarcador: Int)

@Serializable
data class CreateMark(val altitud : Double, val longitud:Double)


