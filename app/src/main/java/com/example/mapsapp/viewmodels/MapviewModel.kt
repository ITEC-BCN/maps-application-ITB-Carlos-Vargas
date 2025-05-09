package com.example.mapsapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapviewModel :ViewModel() {

    var showAlert = mutableStateOf(false)

    private val _altitud = MutableLiveData<Double>()
    val altitud = _altitud

    private val _longitud = MutableLiveData<Double>()
    val longitud = _longitud

    fun updateShowAlert(valor : Boolean){
        showAlert.value = valor
    }

}