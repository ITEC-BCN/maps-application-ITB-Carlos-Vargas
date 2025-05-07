package com.example.mapsapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mapsapp.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MySupabaseClient{

    lateinit var client: SupabaseClient
    lateinit var storage: Storage
    private val supabaseUrl = BuildConfig.SUPABASE_URL
    private val supabaseKey = BuildConfig.SUPABASE_KEY
    constructor(){
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
            install(Storage)

        }
        storage = client.storage

    }

    suspend fun getAllMarcardor(): List<Marcador> {
        return client.from("Marcadores").select().decodeList<Marcador>()
    }

    suspend fun getMarcardor(id: Int): Marcador{
        return client.from("Marcadores").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<Marcador>()
    }

    suspend fun insertMarcardor(marcador: Marcador){
        client.from("Marcadores").insert(marcador)
    }

    suspend fun updateMarcardor( id : Int, newTitle: String, newDescripcion:String){
        client.from("Marcadores").update({
            set("title", newTitle)
            set("descripcion", newDescripcion)
        }) {
            filter {
                eq("id", id)
            }
        }
    }

    suspend fun deleteMarcardor(id: Int){
        client.from("Marcadores").delete{
            filter {
                eq("id", id)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage(imageFile: ByteArray): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val imageName = storage.from("images").upload(path = "image_${fechaHoraActual.format(formato)}.png", data = imageFile)
        return buildImageUrl(imageFileName = imageName.path)
    }

    fun buildImageUrl(imageFileName: String) = "${this.supabaseUrl}/storage/v1/object/public/images/${imageFileName}"


}