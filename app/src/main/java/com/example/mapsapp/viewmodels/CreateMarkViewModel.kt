package com.example.mapsapp.viewmodels

import android.graphics.Bitmap
import android.media.Image
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.data.Marcador
import com.example.mapsapp.data.MySupabaseClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class CreateMarkViewModel :ViewModel() {

    private  val _actutalTitle = MutableLiveData<String>()
    val acltualTitle = _actutalTitle

    private  val _actutalDescipcion = MutableLiveData<String>()
    val actualDescripcion = _actutalDescipcion

    private  val _actualImage = MutableLiveData<String>()

    private val database = MySupabaseClient()




    fun editTitle(newTitle:String){
        _actutalTitle.value = newTitle
    }
    fun editDescripcion(newDescripcion:String){
        _actutalDescipcion.value = newDescripcion
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertNewStudent(title: String, altitud: Double, longitud : Double , descripcion:String ,image: Bitmap?) {
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        CoroutineScope(Dispatchers.IO).launch {
            val imageName = database.uploadImage(stream.toByteArray())
            val marcador = Marcador(null, title, altitud, longitud, descripcion, imageName)
            database.insertMarcardor(marcador)
        }
    }



}