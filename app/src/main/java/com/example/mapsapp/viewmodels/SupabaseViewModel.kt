package com.example.mapsapp.viewmodels

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.Marcador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class SupabaseViewModel: ViewModel() {

    private val database = MyApp.database  // Asegúrate de que MyApp.database esté conectado a Supabase

    private val _marcadorList = MutableLiveData<List<Marcador>>()
    val marcadorList = _marcadorList

    private val _selectedMarcador = MutableLiveData<Marcador>()
    val selectedMarcador = _selectedMarcador

    private val _marcadorTitle = MutableLiveData<String>()
    val marcadorTitle = _marcadorTitle

    private val _marcadorAltitud = MutableLiveData<Double>()
    val marcadorAltitud = _marcadorAltitud

    private val _marcadorLongitud = MutableLiveData<Double>()
    val marcadorLongitud = _marcadorLongitud

    private val _marcadorDescripcion = MutableLiveData<String>()
    val marcadorDescripcion = _marcadorDescripcion

    private val _marcadorImagen = MutableLiveData<String>()
    val marcadorImagen = _marcadorImagen

    var showAlert = mutableStateOf(false) //indicador de alerta crear mark

    private val _showLoading = MutableLiveData<Boolean>(true)
    val showLoading = _showLoading // indicador de carga marks

    fun getAllMarcadors() {
        CoroutineScope(Dispatchers.IO).launch {
            val marcadores = database.getAllMarcardor()
            withContext(Dispatchers.Main) {
                _marcadorList.value = marcadores
                _showLoading.value = false
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertNewMarcador(title: String, altitud: Double, longitud : Double , descripcion:String ,image: Bitmap) {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 0, stream)

        CoroutineScope(Dispatchers.IO).launch {
            val imageName = database.uploadImage(stream.toByteArray())
            val newMarcador = Marcador(null, title, altitud, longitud, descripcion, imageName)
            database.insertMarcardor(newMarcador)
            getAllMarcadors()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateMarcador(id: Int, title: String, descripcion:String, image: Bitmap?){
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteImage(_marcadorImagen.value!!)
            val imageName = database.uploadImage(stream.toByteArray())
            database.updateStudent(id, title, descripcion,  imageName )
        }
    }


    fun deleteStudent(id: Int, image: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteImage(image)
            database.deleteMarcardor(id)
            getAllMarcadors()
        }
    }


    fun getMarcador(id: Int) {

            CoroutineScope(Dispatchers.IO).launch {
                val marcador = database.getMarcardor(id)
                withContext(Dispatchers.Main) {
                    _selectedMarcador.value = marcador
                    _marcadorTitle.value = marcador.title
                    _marcadorAltitud.value = marcador.altitud
                    _marcadorLongitud.value = marcador.longitud
                    _marcadorDescripcion.value = marcador.descripcion
                    _marcadorImagen.value = marcador.imagen
                    _showLoading.value = false
                }
            }

    }

    fun editMarcadorTitle(title: String) {
        _marcadorTitle.value = title
    }

    fun editMarcadorAltitud(altitud: Double) {
        _marcadorAltitud.value = altitud
    }

    fun editMarcadorLongitud(longitud: Double) {
        _marcadorLongitud.value = longitud
    }

    fun editMarcadorDescripcion(descripcion: String) {
        _marcadorDescripcion.value = descripcion
    }
    fun updateShowAlert(valor : Boolean){
        showAlert.value = valor
    }

}