package com.example.mapsapp

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.mapsapp.viewmodels.PermissionViewModel

class MyApp: Application() {


    override fun onCreate() {
        super.onCreate()
    }
}