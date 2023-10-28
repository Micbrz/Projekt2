package com.example.projekt2.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.projekt2.android.navigation.Screenss

@Composable
fun SignIn(navController: NavHostController) {


    Button(onClick = {navController.navigate(Screenss.SignUp.route)})
    {
        Text(text = "Rejestracja")
    }
}