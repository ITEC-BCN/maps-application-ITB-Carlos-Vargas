package com.example.mapsapp.ui.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val route: Destination
)
{
    MAPA(Icons.Default.LocationOn, "Mapa", Destination.Map),
    LISTA(Icons.Default.Info, "Marcadores", Destination.List),
}