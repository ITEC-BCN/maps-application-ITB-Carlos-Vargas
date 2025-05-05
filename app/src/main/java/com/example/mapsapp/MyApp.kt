package com.example.mapsapp

import android.app.Application

import com.example.mapsapp.data.MySupabaseClient


class MyApp: Application() {
    companion object {
        lateinit var database: MySupabaseClient
    }
    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient(
            supabaseUrl = "https://ojkxutdgimhcdhrpqoxu.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9qa3h1dGRnaW1oY2RocnBxb3h1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDU5MjA5NzUsImV4cCI6MjA2MTQ5Njk3NX0.Hn7WD_e7f3UhntdJuVVNiV6SjtPw4NGrd8p2isXv2UE"
        )
    }

}