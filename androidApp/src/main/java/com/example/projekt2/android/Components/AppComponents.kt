package com.example.projekt2.android.Components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projekt2.android.BgColor
import com.example.projekt2.android.Primary
import com.example.projekt2.android.R
import com.example.projekt2.android.Secondary
import com.example.projekt2.android.TextColor


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

@Composable
fun DividerTextComponent(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            thickness = 1.dp
        )
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp,
            color = TextColor,
            text = stringResource(R.string.or)
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            thickness = 1.dp

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailText(labelValue: String,painterResource: Painter,
              onTextSelected: (String) -> Unit,errorStatus:Boolean = false) {
    val email = remember { mutableStateOf("") }
    val maxchar = 25
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Green,
            unfocusedBorderColor = Green
        ),
        shape = RoundedCornerShape(20),
        value = email.value,
        onValueChange = { if(it.length <= maxchar){
                                email.value = it
                            }
                        onTextSelected(it)},
        label = {Text(text = labelValue,
            color = TextColor)
            },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
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
    val passwordVisible = remember{
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary

        ),

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Done),
        shape = RoundedCornerShape(20),
        value = password.value,
        onValueChange = {
                        if(it.length <= maxchar){
                            password.value = it
                        }
                        onTextSelected(it)},
        label = {Text(text = labelValue,
            color = TextColor)},
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            var description = if(passwordVisible.value){
                stringResource(id = R.string.Hidepassword)
            }else{
                stringResource(id = R.string.Showpassword)
            }
            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),


        isError = !errorStatus


    )

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepeatPassword(labelValue : String,painterResource:Painter,
                   onTextSelected:(String) -> Unit,errorStatus:Boolean = false){
    val passwordVisible = remember{
        mutableStateOf(false)
    }
    val maxchar = 15
    val repeatpassword = remember{mutableStateOf("")}
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            containerColor = BgColor

        ),

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Done),
        shape = RoundedCornerShape(20),
        value = repeatpassword.value,
        onValueChange = {
            if(it.length <= maxchar){
                repeatpassword.value = it
            }
            onTextSelected(it)},
        label = {Text(text = labelValue,
            color = TextColor)},
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            var description = if(passwordVisible.value){
                stringResource(id = R.string.Hidepassword)
            }else{
                stringResource(id = R.string.Showpassword)
            }
            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),


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
            modifier = Modifier.fillMaxWidth()
                .heightIn(40.dp)
                .background(
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

@Composable
fun HomeScreenn(onTextSelected: (String) -> Unit){
    val initialText = "Masz już konto? Przejdź do "
    val logintext= "Logowania"

    val annotatedString = buildAnnotatedString{
        withStyle(style=SpanStyle(color = TextColor)){append(initialText)}
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation(tag = logintext, annotation = logintext)
            append(logintext)}

        }
    ClickableText(modifier=Modifier.fillMaxWidth(),style=TextStyle(textAlign = TextAlign.Center),
        text = annotatedString,
        onClick = {offset ->

        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->
                Log.d("ClickableTextComponent", "{${span.item}}")

                if (span.item == logintext) {
                    onTextSelected(span.item)
                }
            }})
}
@Composable
fun SignInScreenn( onClick: (String) -> Unit ){
    val initialText = "Nie masz jeszcze konta? Przejdź do "
    val logintext= "Rejestracji"

    val annotatedString = buildAnnotatedString{
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation(tag = logintext, annotation = logintext)
            append(logintext)}}
    ClickableText(modifier=Modifier.fillMaxWidth(),style=TextStyle(textAlign = TextAlign.Center),
        text=annotatedString , onClick={onClick("Home")})
}
@Composable
fun forgot(onClick: (String) -> Unit){
    val initialText = "Zapomniałeś hasła? "
    val logintext= "Zresetuj je"

    val annotatedString = buildAnnotatedString{
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation(tag = logintext, annotation = logintext)
            append(logintext)}}
    ClickableText(modifier=Modifier.fillMaxWidth(),style=TextStyle(textAlign = TextAlign.Center),
        text=annotatedString , onClick={onClick("Home")})
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
@Composable
fun BackButton(onClick: () -> Unit){
    IconButton(onClick = onClick){
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black
        )
    }
}
@Composable
fun Search(onSearchQueryChange: (String) -> Unit , onSearch: () -> Unit){
    var searchQuery by remember {mutableStateOf("")}
    Column{
        TextField(
            value = searchQuery,
            onValueChange = {onSearchQueryChange(it)},
            label = {Text("Wyszukaj użytkownika")},
        )
        Button(
            onClick = {onSearch()},
        )
        {
            Text("Szukaj")
        }
    }
}









