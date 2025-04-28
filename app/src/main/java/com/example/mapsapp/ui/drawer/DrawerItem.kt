package com.example.mapsapp.ui.drawer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mapsapp.ui.navigation.Destination

enum class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val route: Destination
)
{
    MAPA(Icons.Default.LocationOn, "Mapa", Destination.Map),
    LISTA(Icons.Default.Info, "Marcadores", Destination.List),
}