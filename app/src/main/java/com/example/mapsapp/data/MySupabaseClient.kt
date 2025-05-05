package com.example.mapsapp.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

class MySupabaseClient() {

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

    suspend fun getMarcardor(id: Int): Marcador{
        return client.from("Marcardores").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<Marcador>()
    }

    suspend fun insertMarcardor(marcador: Marcador){
        client.from("Marcardores").insert(marcador)
    }

    suspend fun updateMarcardor( id : Int, newTitle: String, newDescripcion:String){
        client.from("Marcardores").update({
            set("title", newTitle)
            set("descripcion", newDescripcion)
        }) {
            filter {
                eq("id", id)
            }
        }
    }

    suspend fun deleteMarcardor(id: Int){
        client.from("Marcardores").delete{
            filter {
                eq("id", id)
            }
        }
    }


}