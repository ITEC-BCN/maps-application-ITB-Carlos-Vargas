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


}