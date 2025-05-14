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
    private val supabaseUrl = "https://ojkxutdgimhcdhrpqoxu.supabase.co"
    private val supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9qa3h1dGRnaW1oY2RocnBxb3h1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDU5MjA5NzUsImV4cCI6MjA2MTQ5Njk3NX0.Hn7WD_e7f3UhntdJuVVNiV6SjtPw4NGrd8p2isXv2UE"
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

    suspend fun deleteImage(imageName: String){
        val imgName = imageName.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        client.storage.from("images").delete(imgName)
    }

    suspend fun updateStudent(id : Int, newTitle: String, newDescripcion:String, imageName: String) {
        client.from("Marcadores").update({
            set("title", newTitle)
            set("descripcion", newDescripcion)
            set("imagen", imageName)
        }) {
            filter {
                eq("id", id)
            }
        }
    }



}