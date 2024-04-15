package com.example.projekt2.android.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projekt2.android.Data.ViewModel.ForgotPasswordViewModel
import com.example.projekt2.android.navigation.Routes

@Composable
fun ForgotPassword(navHostController: NavHostController, resetpassword: ForgotPasswordViewModel = viewModel()){
    var email by remember {mutableStateOf("")}
    val resetPasswordResult by resetpassword.resetPassword(email).observeAsState()
    var resetButtonClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(28.dp),
        ) {


            item{
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Button(
                onClick = {resetButtonClicked = true
                    if (email.isNotEmpty()) {
                        resetpassword.resetPassword(email)
                    } else {
                        Toast.makeText(
                            context,
                            "Wprowadź adres email",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                          },
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Text("Resetuj hasło")
            }}

                if (resetButtonClicked && resetPasswordResult != null) {
                    item {
                        if (resetPasswordResult == true) {
                            navHostController.navigate(Routes.SignIn.routes)
                        } else {
                            Toast.makeText(
                                LocalContext.current,
                                "Błąd resetowania hasła",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
}
