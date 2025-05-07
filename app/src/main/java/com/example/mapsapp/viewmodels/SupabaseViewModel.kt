package com.example.mapsapp.viewmodels

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
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

    val database = MyApp.database  // Asegúrate de que MyApp.database esté conectado a Supabase

    private val _marcadorList = MutableLiveData<List<Marcador>>()
    val marcadorList = _marcadorList

    private var _selectedMarcador: Marcador? = null

    private val _marcadorTitle = MutableLiveData<String>()
    val marcadorTitle = _marcadorTitle

    private val _marcadorAltitud = MutableLiveData<Double>()
    val marcadorAltitud = _marcadorAltitud

    private val _marcadorLongitud = MutableLiveData<Double>()
    val marcadorLongitud = _marcadorLongitud

    private val _marcadorDescripcion = MutableLiveData<String>()
    val marcadorDescripcion = _marcadorDescripcion

    fun getAllMarcadors() {
        CoroutineScope(Dispatchers.IO).launch {
            val marcadores = database.getAllMarcardor()
            withContext(Dispatchers.Main) {
                _marcadorList.value = marcadores
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertNewMarcador(title: String, longitud: Double, altitud: Double, descripcion: String, image: Bitmap) {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 0, stream)

        CoroutineScope(Dispatchers.IO).launch {
            val imageName = database.uploadImage(stream.toByteArray())
            val newMarcador = Marcador(title = title,
                longitud = longitud,
                altitud = altitud,
                descripcion = descripcion,
                imagen = imageName)
            database.insertMarcardor(newMarcador)
            getAllMarcadors()
        }
    }

    fun updateMarcador(id: Int, title: String, descripcion: String) {
        CoroutineScope(Dispatchers.IO).launch {
            database.updateMarcardor(id, title, descripcion)
            getAllMarcadors()
        }
    }

    fun deleteMarcador(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteMarcardor(id)
            getAllMarcadors()
        }

    }

    fun getMarcador(id: Int) {
        if (_selectedMarcador == null) {
            CoroutineScope(Dispatchers.IO).launch {
                val marcador = database.getMarcardor(id)
                withContext(Dispatchers.Main) {
                    _selectedMarcador = marcador
                    _marcadorTitle.value = marcador.title
                    _marcadorAltitud.value = marcador.altitud
                    _marcadorLongitud.value = marcador.longitud
                    _marcadorDescripcion.value = marcador.descripcion
                }
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
}