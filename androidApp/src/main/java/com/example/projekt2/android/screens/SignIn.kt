package com.example.projekt2.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projekt2.android.Components.EmailText
import com.example.projekt2.android.Components.NormalTextComponent
import com.example.projekt2.android.Components.PasswordText
import com.example.projekt2.android.Components.RegistrationButton
import com.example.projekt2.android.Components.SignInScreenn
import com.example.projekt2.android.Data.LoginUIEvent
import com.example.projekt2.android.Data.LoginViewModel
import com.example.projekt2.android.Data.SignUpUIEvent
import com.example.projekt2.android.Data.SignUpViewModel

import com.example.projekt2.android.R
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens

@Composable
fun SignIn(loginViewModel: LoginViewModel = viewModel()) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    )
    {
        Column(){
            Spacer(modifier = Modifier.height(20.dp))
            NormalTextComponent(loginText = stringResource(id= R.string.logIn))
            EmailText(
                labelValue = stringResource(id=R.string.email),
                painterResource = painterResource(id = R.drawable.message),
                onTextSelected={
                    loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                } )
            PasswordText(
                labelValue = stringResource(id=R.string.password),
                painterResource = painterResource(id = R.drawable.ic_lock),
                onTextSelected={
                    loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                }
            )
            RegistrationButton(
                value = stringResource(id=R.string.Login),
                onButtonClicked = {
                    loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                },
                isEnabled = loginViewModel.allValidationsPassed.value

            )
            SignInScreenn(onClick = { PostOfficeAppRouter.navigateTo(Screens.SignUp)})
        }
    }


}

