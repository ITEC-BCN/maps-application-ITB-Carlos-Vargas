package com.example.mapsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.drawer.Destination
import com.example.mapsapp.ui.drawer.DrawerScreens

import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.PermissionsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()
    NavHost(navController, Permisos){
        composable<Permisos>{
            PermissionsScreen{  navController.navigate(Destination.Login) }
        }
        composable<Destination.Login> {
            NavigationLogin()
            }
        }
}