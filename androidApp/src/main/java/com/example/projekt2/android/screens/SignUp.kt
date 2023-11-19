package com.example.projekt2.android.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.projekt2.android.Components.EmailText
import com.example.projekt2.android.Components.NormalTextComponent
import com.example.projekt2.android.Components.PasswordText
import com.example.projekt2.android.Components.RegistrationButton
import com.example.projekt2.android.Components.RepeatPassword
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.projekt2.android.Components.HomeScreenn
import com.example.projekt2.android.Data.SignUpUIEvent
import com.example.projekt2.android.Data.SignUpViewModel

import com.example.projekt2.android.R
import com.example.projekt2.android.navigation.PostOfficeAppRouter
//import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens


@Composable
fun SignUp(SignUpViewModel: SignUpViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Surface(

            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)

        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                NormalTextComponent(loginText = stringResource(id = R.string.RegisterText))
                Spacer(modifier=Modifier.height(20.dp))
                EmailText(
                    labelValue = stringResource(id=R.string.email),
                    painterResource = painterResource(id = R.drawable.profile),
                    errorStatus = SignUpViewModel.SignUpUIState.value.emailError,
                    onTextSelected={
                        SignUpViewModel.onEvent(SignUpUIEvent.EmailChanged(it))
                    } )
                Spacer(modifier=Modifier.height(10.dp))
                PasswordText(
                    labelValue = stringResource(id=R.string.password),
                    painterResource = painterResource(id = R.drawable.ic_lock),
                    errorStatus = SignUpViewModel.SignUpUIState.value.passwordError,
                    onTextSelected={
                        SignUpViewModel.onEvent(SignUpUIEvent.PasswordChanged(it))
                    }
                )
                Spacer(modifier=Modifier.height(10.dp))
                RepeatPassword(
                    labelValue = stringResource(id=R.string.repeatpassword),
                    painterResource = painterResource(id=R.drawable.ic_lock),
                    errorStatus = SignUpViewModel.SignUpUIState.value.repeatpasswordError,
                    onTextSelected={
                        SignUpViewModel.onEvent(SignUpUIEvent.RepeatPasswordChanged(it))
                    })
                Spacer(modifier=Modifier.height(10.dp))
                RegistrationButton(

                    value = stringResource(id=R.string.register),
                    onButtonClicked = {
                        SignUpViewModel.onEvent(SignUpUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = SignUpViewModel.allValidationsPassed.value
                )
                Spacer(modifier=Modifier.height(10.dp))
                HomeScreenn(onTextSelected={ PostOfficeAppRouter.navigateTo(Screens.SignIn)})

            }
        }
    }

}






