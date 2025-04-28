package com.example.mapsapp.ui.drawer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.navigation.Destination
import com.example.mapsapp.ui.navigation.Permisos
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.MarkerListScreen
import com.example.mapsapp.ui.screens.PermissionsScreen

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