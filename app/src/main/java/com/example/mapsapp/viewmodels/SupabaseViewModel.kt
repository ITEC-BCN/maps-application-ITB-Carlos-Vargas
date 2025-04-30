package com.example.mapsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.Marcador
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SupabaseViewModel: ViewModel() {
    /*val database = MyApp.database
    private val _studentName = MutableLiveData<String>()
    val studentName = _studentName
    private val _studentMark = MutableLiveData<String>()
    val studentMark = _studentMark


    fun insertNewMarcador(title: String, longitud: Double, altitud : Double, descripcion: String) {
        val newMarcador = Marcador(title = title, longitud = longitud, altitud = altitud, descripcion = descripcion )
        CoroutineScope(Dispatchers.IO).launch {
            database.insertMarcardor(newMarcador)
            getAllMarcardor()
        }
    }*/
    val database = MyApp.database

    private val _marcadorList = MutableLiveData<List<Marcador>>()
    val marcadorList = _marcadorList

    private var _selectedMarcador: Marcador? = null

    private val _studentName = MutableLiveData<String>()
    val studentName = _studentName

    private val _studentMark = MutableLiveData<String>()
    val studentMark = _studentMark

    private val _marcadorTitle = MutableLiveData<String>()
    val marcadorTitle = _marcadorTitle

    private val _marcadorAltitud= MutableLiveData<Double>()
    val marcadorAltitud = _marcadorAltitud

    private val _marcadorLongitd = MutableLiveData<Double>()
    val marcadorLongitud = _marcadorLongitd

    private val _marcadorDescripcion = MutableLiveData<String>()
    val marcadorDescripcion = _marcadorDescripcion

    fun getAllMarcadors() {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseMarcadors = database.getAllMarcardor()
            withContext(Dispatchers.Main) {
                _marcadorList.value = databaseMarcadors
            }
        }
    }

    fun insertNewMarcador(title: String, longitud: Double, altitud : Double, descripcion: String) {
        val newMarcador = Marcador(title = title, longitud = longitud, altitud = altitud, descripcion = descripcion )
        CoroutineScope(Dispatchers.IO).launch {
            database.insertMarcardor(newMarcador)
            getAllMarcadors()
        }
    }

    fun updateMarcador(id: String, name: String, mark: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.updateMarcardor(id, name, mark.toDouble())
        }
    }

    fun deleteMarcador(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteMarcardor(id)
            getAllMarcadors()
        }
    }

    fun getMarcador(id: String){
        if(_selectedMarcador == null){
            CoroutineScope(Dispatchers.IO).launch {
                val student = database.getMarcardor(id)
                withContext(Dispatchers.Main) {
                    _selectedMarcador = student
                    _studentName.value = student.name
                    _studentMark.value = student.mark.toString()
                }
            }
        }
    }

    fun editMarcadorName(name: String) {
        _studentName.value = name
    }

    fun editMarcadorMark(mark: String) {
        _studentMark.value = mark
    }
}