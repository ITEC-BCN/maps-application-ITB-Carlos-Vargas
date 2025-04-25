package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.PermissionsScreen

@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()
    NavHost(navController, Permisos){
        composable<Permisos>{
            PermissionsScreen{  navController.navigate(Destination.Map) }
        }
        composable<Destination.Map> {
            MapScreen()
            }
        }
}