package com.example.mapsapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.AuthViewModelFactory
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModel

@Composable
fun RegisterScreen( navigateToMap: @Composable () -> Unit){
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(SharedPreferencesHelper(context)))
    val authState: AuthState by viewModel.authState.observeAsState(AuthState.Unauthenticated)
    val showError : Boolean by viewModel.showError.observeAsState(false)

    val correo: String? by viewModel.email.observeAsState("")
    val paswword: String? by viewModel.password.observeAsState("")
    if(authState == AuthState.Authenticated){
        navigateToMap()
    }
    else{
        if (showError) {
            val errorMessage = (authState as AuthState.Error).message
            if (errorMessage!!.contains("invalid_credentials")) {
                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "An error has ocurred", Toast.LENGTH_LONG).show()
            }
            viewModel.errorMessageShowed()
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ){
            TextField(
                value = correo!!, label = { Text(text = "Correo electronico") },
                onValueChange = {
                    viewModel.editEmail(it)

                }
            )

            TextField(
                value = paswword!!, label = { Text(text = "Contraseña") },
                onValueChange = {
                    viewModel.editPassword(it)

                }
            )

            Button(onClick = {
                viewModel.signUp()
            }) {
                Text(text = "Register")
            }

        }

    }
}