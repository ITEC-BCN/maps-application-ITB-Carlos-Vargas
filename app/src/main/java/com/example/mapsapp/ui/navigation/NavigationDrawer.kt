package com.example.mapsapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.MarkerListScreen

@Composable
fun NavigationDrawer(navController: NavHostController,
                     contentPadding: PaddingValues
){
    NavHost(navController, Destination.Map){
        composable<Destination.Map>{
            MapScreen(contentPadding)
        }
        composable<Destination.List> {
            MarkerListScreen(contentPadding)
        }
    }
}