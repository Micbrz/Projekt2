package com.example.projekt2.android.Components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.projekt2.android.Primary
import com.example.projekt2.android.Secondary
import com.example.projekt2.android.TextColor
import com.example.projekt2.android.navigation.Screenss


@Composable
fun NormalTextComponent(loginText: String) {
    Text(
        text = loginText,
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
fun EmailText(labelValue: String,painterResource: Painter,
              onTextSelected: (String) -> Unit,errorStatus:Boolean = false) {
    val email = remember { mutableStateOf("") }
    val maxchar = 25
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),maxLines = 1,

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,

        ),
        shape = RoundedCornerShape(20),
        value = email.value,
        onValueChange = { if(it.length <= maxchar){
                                email.value = it
                            }
                        onTextSelected(it)},
        label = {Text(text = labelValue,modifier = Modifier.fillMaxWidth(),
            color = TextColor)
            },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = !errorStatus
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordText(labelValue : String,onTextSelected: (String) -> Unit,
                 painterResource: Painter,errorStatus:Boolean = false){
    val password = remember{mutableStateOf("")}
    val maxchar = 15
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary

        ),
        shape = RoundedCornerShape(20),
        value = password.value,
        onValueChange = {
                        if(it.length <= maxchar){
                            password.value = it
                        }
                        onTextSelected(it)},
        label = {Text(text = labelValue,modifier = Modifier.fillMaxWidth(),
            color = TextColor)},
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = !errorStatus
    )
}
@Composable
fun RepeatPassword(labelValue : String,painterResource:Painter,
                   onTextSelected:(String) -> Unit,errorStatus:Boolean = false){
    val maxChar = 15
    val repeatPassword = remember{mutableStateOf("")}
    OutlinedTextField(
        value = repeatPassword.value,
        onValueChange = {
            if(it.length <= maxChar)
            {
                repeatPassword.value = it
            }
            onTextSelected(it)},

        shape = RoundedCornerShape(20),
        label = {Text(text = labelValue) },
        modifier = Modifier.fillMaxWidth(),maxLines = 1,
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        isError = !errorStatus
    )




}

@Composable
fun RegistrationButton(value : String, onButtonClicked: () -> Unit, isEnabled: Boolean = false){
    Button(onClick = { onButtonClicked.invoke()},
        modifier = Modifier.fillMaxWidth()
            .heightIn(20.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.White),
        enabled = isEnabled)

    {
        Box(
            modifier = Modifier.fillMaxWidth().background(
                brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                shape = RoundedCornerShape(20.dp)
            ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = value
            )
        }
    }
}
/*
@Composable
fun HomeScreenn(onTextSelected: (String) -> Unit,navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Home Screen",

                fontWeight = FontWeight.Bold
            )
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                shape = MaterialTheme.shapes.medium,

                modifier = Modifier.padding(5.dp),
                onClick = {{navController.navigate(Screenss.Registration.route)}
                }
            ) {
                Text(
                    text = "Go to Details",
                    modifier = Modifier.padding(5.dp),


                )
            }
        }
    }
}


*/
@Composable
fun HomeScreenn(/*onTextSelected: (String) -> Unit*/ onClick: (String) -> Unit ){
    val initialText = "Masz już konto? Przejdź do "
    val logintext= "Logowania"

    val annotatedString = buildAnnotatedString{
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation(tag = logintext, annotation = logintext)
            append(logintext)}}
    ClickableText(modifier = Modifier.fillMaxWidth(),
        style = TextStyle(textAlign = TextAlign.Center),
        text=annotatedString , onClick={onClick("Home")})
}
@Composable
fun SignInScreenn(/*onTextSelected: (String) -> Unit*/ onClick: (String) -> Unit ){
    val initialText = "Nie masz jeszcz konta? Przejdź do "
    val logintext= "Rejestracji"

    val annotatedString = buildAnnotatedString{
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation(tag = logintext, annotation = logintext)
            append(logintext)}}
    ClickableText(text=annotatedString , onClick={onClick("Home")})
}
/*

        }
    }

    ClickableText(
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontSize =  21.sp,
            fontStyle = FontStyle.Normal
        ),
        text = annotatedString,*/
        //tutaj
                /*offset ->

            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    Log.d("ClickableLoginTextComponent", "{${span.item}}")

                    if (span.item == logintext) {
                        onTextSelected(span.item)
                    }
                }

        }

    )
}*/









