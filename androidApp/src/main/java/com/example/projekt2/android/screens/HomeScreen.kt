package com.example.projekt2.android.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projekt2.android.Components.RegistrationButton

import com.example.projekt2.android.Data.SignUpViewModel
import com.example.projekt2.android.R
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens

@Composable
fun HomeScreen(loginViewModel: SignUpViewModel = viewModel()) {
    val context = LocalContext.current
    var expanded by remember {mutableStateOf (false)}
    Column(){

        Button(onClick={expanded = true})
        {Text("Menu")}
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(text ={Text("Wybierz opcje")},
                    onClick = {
                        expanded = false
                        Toast.makeText(context,"wybrano opcje: ",Toast.LENGTH_LONG)
                    })

            RegistrationButton(
                value = stringResource(id=R.string.Profile),
                onButtonClicked={
                    PostOfficeAppRouter.navigateTo(Screens.Profile)
                },
                isEnabled = true
            )
                RegistrationButton(
                    value = stringResource(id= R.string.logout),
                    onButtonClicked = {
                        loginViewModel.logout()
                        PostOfficeAppRouter.navigateTo(Screens.SignIn)

                    },
                    isEnabled = true
                )
            RegistrationButton(
                value = stringResource(id=R.string.Chat),
                onButtonClicked = {
                    PostOfficeAppRouter.navigateTo(Screens.ChatApplication)
                },
                isEnabled=true
            )



    }
}}