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

import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.PermissionsScreen
import com.example.mapsapp.ui.screens.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()
    NavHost(navController, Destination.Permisos){
        composable<Destination.Permisos>{
            PermissionsScreen{  navController.navigate(Destination.Login) }
        }
        composable<Destination.Login> {
            LoginScreen(navigateToMap = {
                navController.navigate(Destination.Drawer)
            },
                navigateToRegister = {
                    navController.navigate(Destination.Resgister)
                })
        }

        composable<Destination.Resgister> {
            RegisterScreen(navigateToMap = {
                navController.navigate(Destination.Drawer) {
                    popUpTo(0)
                }
            },
                navigateToLogin ={
                    navController.navigate(Destination.Login)
                } )
        }

        composable<Destination.Drawer> {
            DrawerScreens {

                navController.navigate(Destination.Login) {
                    popUpTo<Destination.Login> { inclusive = true }
                }
            }// solo este tiene navigation anidada
        }
    }
}