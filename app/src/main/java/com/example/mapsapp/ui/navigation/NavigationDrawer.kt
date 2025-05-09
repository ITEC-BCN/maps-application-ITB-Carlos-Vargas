package com.example.mapsapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.MarkerListScreen
import androidx.navigation.toRoute
import com.example.mapsapp.ui.screens.CreateMarkerScreen

@Composable
fun NavigationDrawer(navController: NavHostController,
                     contentPadding: PaddingValues
){
    NavHost(navController, Destination.Map){
        composable<Destination.Map>{
            MapScreen(
                contentPadding,
                navigateToNext = { altitud, longitud ->
                navController.navigate(CreateMark(altitud,longitud))
            })
        }
        composable<Destination.List> {
            MarkerListScreen(contentPadding)
        }
        composable<CreateMark> { backStrackEntry ->
            val detall = backStrackEntry.toRoute<CreateMark>()
            CreateMarkerScreen(contentPadding ,detall.altitud, detall.longitud)
        }
    }
}