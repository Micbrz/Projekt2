package com.example.projekt2.android.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projekt2.android.Primary

import com.example.projekt2.android.Secondary
import com.example.projekt2.android.TextColor


@Composable
fun NormalTextComponent(LoginText: String) {
    Text(
        text = LoginText,
        modifier = Modifier.fillMaxWidth().heightIn(min=50.dp),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ),
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun EmailText() {
    var Email by remember { mutableStateOf("") }

    OutlinedTextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary
        ),
        shape = RoundedCornerShape(20),
        value = Email,
        onValueChange = { Email = it },
        label = { Text("E-mail") }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordText(){
    var Password by remember{mutableStateOf("")}

    OutlinedTextField(
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary

        ),
        shape = RoundedCornerShape(20),
        value = Password,
        onValueChange = {Password = it},
        label = {Text("Hasło")},
        //leadingIcon = {Icon(painter = painterResource(id = R.drawable.profile), contentDescription="")}
    )
}
@Composable
fun RepeatPassword(){
    val maxChar = 10
    var RepeatPassword by remember{mutableStateOf("")}
    OutlinedTextField(
        value = RepeatPassword,
        onValueChange = {
            if(it.length <= maxChar)
            {
                RepeatPassword = it
            }},
        shape = RoundedCornerShape(20),


        label = {Text("Powtorz Haslo") },
        modifier = Modifier.fillMaxWidth(),maxLines = 1

    )




}

@Composable
fun RegistrationButton(){
    Button(onClick = {},
        modifier = Modifier.fillMaxWidth()
            .heightIn(20.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.White))
    {
        Box(
            modifier = Modifier.fillMaxWidth().background(
                brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                shape = RoundedCornerShape(20.dp)
            ),
            contentAlignment = Alignment.Center
        ){
            Text(text = "Rejestracja")
        }
    }
}








