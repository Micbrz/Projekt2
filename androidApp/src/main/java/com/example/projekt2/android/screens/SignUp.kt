package com.example.projekt2.android.screens

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

import com.example.projekt2.android.R

@Composable
fun SignUp() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ){
        Column() {
            Spacer(modifier = Modifier.height(2.dp))
            NormalTextComponent(LoginText = stringResource(id = R.string.RegisterText))
            EmailText()
            PasswordText()
            RepeatPassword()
            RegistrationButton()
        }
    }
}
@Preview
@Composable
fun DefaultPreview()
{

    Surface(


        modifier = Modifier.fillMaxSize()
        .padding(28.dp)

    )
    {
        SignUp()




    }
}
