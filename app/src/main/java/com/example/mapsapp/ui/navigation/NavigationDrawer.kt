package com.example.mapsapp.ui.navigation

import com.example.mapsapp.ui.drawer.Destination

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.mapsapp.ui.screens.MarkerListScreen
import androidx.navigation.toRoute

import com.example.mapsapp.ui.screens.CreateMarkerScreen
import com.example.mapsapp.ui.screens.DetailsMarkScreen
import com.example.mapsapp.ui.screens.MapScreen





@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationDrawer(navController: NavHostController,
                     contentPadding: PaddingValues
){
    NavHost(navController, Destination.Map){
        composable<Destination.Map>{
            MapScreen(
                contentPadding,
                navigateToNext = { altitud, longitud ->
                navController.navigate(Destination.CreateMark(altitud,longitud))
            })
        }
        composable<Destination.List> {
            MarkerListScreen(contentPadding,
                navigateToNext = { id ->
                    navController.navigate(Destination.Details(id))
                })
        }
        composable<Destination.CreateMark> { backStrackEntry ->
            val detall = backStrackEntry.toRoute<Destination.CreateMark>()
            CreateMarkerScreen(
                contentPadding,
                detall.altitud,
                detall.longitud,
                navigateToNext = {
                    navController.navigate(Destination.Map)
                })
        }
        composable<Destination.Details> {
            backStrackEntry ->
            val detall = backStrackEntry.toRoute<Destination.Details>()
            DetailsMarkScreen(
                contentPadding,
                detall.idMarcador,
                navigateToNext = {
                    navController.popBackStack(Destination.List, inclusive = true)
                    navController.navigate(Destination.List)
                }
            )
        }
    }
}