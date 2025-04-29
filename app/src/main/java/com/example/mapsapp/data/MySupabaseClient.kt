package com.example.mapsapp.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

class MySupabaseClient {

    lateinit var client: SupabaseClient

    constructor(supabaseUrl: String, supabaseKey: String): this(){
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
        }
    }

    suspend fun getAllMarcardor(): List<Marcador> {
        return client.from("Marcardores").select().decodeList<Marcador>()
    }

    suspend fun getMarcardor(title: String): Marcador{
        return client.from("Marcardores").select {
            filter {
                eq("title", title)
            }
        }.decodeSingle<Marcador>()
    }

    suspend fun insertMarcardor(student: Marcador){
        client.from("Marcardores").insert(student)
    }

    suspend fun updateMarcardor( oldTitle: String, newTitle: String,snippet:String){
        client.from("Marcardores").update({
            set("title", newTitle)
            set("snippet", snippet)
        }) {
            filter {
                eq("title", oldTitle)
            }
        }
    }

    suspend fun deleteMarcardor(title: String){
        client.from("Marcardores").delete{
            filter {
                eq("title", title)
            }
        }
    }


}