package com.example.mapsapp.ui.drawer

import kotlinx.serialization.Serializable

sealed class Destination {

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