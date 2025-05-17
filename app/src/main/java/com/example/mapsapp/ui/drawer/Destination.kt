package com.example.mapsapp.ui.drawer

import kotlinx.serialization.Serializable

sealed class Destination {

    @Serializable
    object Permisos

    @Serializable
    data class  Details(val idMarcador: Int)

    @Serializable
    data class CreateMark(val altitud : Double, val longitud:Double)

    @Serializable
    object Login :Destination()

    @Serializable
    object Resgister :Destination()

    @Serializable
    object Map: Destination()

    @Serializable
    object List: Destination()

    @Serializable
    object Drawer :Destination()

}