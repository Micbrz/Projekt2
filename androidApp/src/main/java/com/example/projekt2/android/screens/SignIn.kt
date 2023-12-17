package com.example.projekt2.android.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projekt2.android.Components.EmailText
import com.example.projekt2.android.Components.NormalTextComponent
import com.example.projekt2.android.Components.PasswordText
import com.example.projekt2.android.Components.RegistrationButton
import com.example.projekt2.android.Components.SignInScreenn
import com.example.projekt2.android.Data.Login.LoginUIEvent
import com.example.projekt2.android.Data.Login.LoginViewModel
import com.example.projekt2.android.R
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens

@Composable
fun SignIn(loginViewModel: LoginViewModel = viewModel()) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Surface(

            modifier = Modifier.fillMaxSize()
                .padding(28.dp)
                .background(Color.White)

        )
        {
            Column() {
                Spacer(modifier = Modifier.height(20.dp))
                NormalTextComponent(loginText = stringResource(id = R.string.logIn))
                EmailText(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.message),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    })
                Spacer(modifier = Modifier.height(10.dp))
                PasswordText(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.ic_lock),
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                RegistrationButton(
                    value = stringResource(id = R.string.Login),
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                        if (loginViewModel.loginInProgress.value) {
                            Toast.makeText(context, "Logowanie udane!", Toast.LENGTH_SHORT)
                        }
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(10.dp))
                SignInScreenn(onClick = { PostOfficeAppRouter.navigateTo(Screens.SignUp) })
            }
        }

    }
}

