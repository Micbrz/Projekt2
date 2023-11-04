package com.example.projekt2.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Column{
        Text(text = "Home screenNNNNNNNNNNNNNNNNNNNNNNNNn")
        RegistrationButton(
            value = stringResource(id= R.string.logout),
            onButtonClicked = {
                loginViewModel.logout()
                PostOfficeAppRouter.navigateTo(Screens.SignIn)

            },
            isEnabled = true
            )
        RegistrationButton(
            value = stringResource(id=R.string.Profile),
            onButtonClicked={
                PostOfficeAppRouter.navigateTo(Screens.Profile)
            },
            isEnabled = true
        )

    }
}