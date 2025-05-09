package com.example.mapsapp.viewmodels

import android.media.Image
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.data.Marcador

class CreateMarkViewModel :ViewModel() {

    private  val _actutalTitle = MutableLiveData<String>()
    val acltualTitle = _actutalTitle

    private  val _actutalDescipcion = MutableLiveData<String>()
    val actualDescripcion = _actutalDescipcion

    private  val _actualImage = MutableLiveData<String>()




    fun editTitle(newTitle:String){
        _actutalTitle.value = newTitle
    }
    fun editDescripcion(newDescripcion:String){
        _actutalDescipcion.value = newDescripcion
    }



}