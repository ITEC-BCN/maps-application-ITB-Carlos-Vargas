package com.example.mapsapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Marcador(
    val title :String,
    val altitud : Double,
    val longitud: Double,
    val descripcion: String
)
