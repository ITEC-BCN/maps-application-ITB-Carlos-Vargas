package com.example.mapsapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Permisos

@Serializable
object  Details

sealed class Destination {

    @Serializable
    object Map: Destination()

    @Serializable
    object List: Destination()
}
