package com.example.mapsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.drawer.Destination
import com.example.mapsapp.ui.drawer.DrawerScreens
import com.example.mapsapp.ui.screens.LoginScreen
import com.example.mapsapp.ui.screens.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationLogin(){
    val navController = rememberNavController()
    NavHost(navController, Destination.Login){
        composable<Destination.Login>{
            LoginScreen(
                navigateToMap = {
                    DrawerScreens()
                }
            )
        }
        composable<Destination.Resgister>{
            RegisterScreen(
                navigateToMap = {
                DrawerScreens()
            })
        }
        composable<Destination.Drawer>{
            DrawerScreens()
        }

    }
}