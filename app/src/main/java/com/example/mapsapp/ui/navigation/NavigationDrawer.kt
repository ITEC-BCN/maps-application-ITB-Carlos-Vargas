

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.mapsapp.ui.screens.MarkerListScreen
import androidx.navigation.toRoute
import com.example.mapsapp.ui.navigation.CreateMark
import com.example.mapsapp.ui.navigation.Destination
import com.example.mapsapp.ui.navigation.Details
import com.example.mapsapp.ui.screens.CreateMarkerScreen
import com.example.mapsapp.ui.screens.DetailsMarkScreen
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.data.Marcador


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
                navController.navigate(CreateMark(altitud,longitud))
            })
        }
        composable<Destination.List> {
            MarkerListScreen(contentPadding,
                navigateToNext = { id ->
                    navController.navigate(Details(id))
                })
        }
        composable<CreateMark> { backStrackEntry ->
            val detall = backStrackEntry.toRoute<CreateMark>()
            CreateMarkerScreen(
                contentPadding,
                detall.altitud,
                detall.longitud,
                navigateToNext = {
                    navController.navigate(Destination.Map)
                })
        }
        composable<Details> {
            backStrackEntry ->
            val detall = backStrackEntry.toRoute<Details>()
            DetailsMarkScreen(
                contentPadding,
                detall.idMarcador,
                navigateToNext = {
                    navController.navigate(Destination.List){
                        popUpTo(Destination.List) { inclusive = true }
                    }
                }
            )
        }
    }
}