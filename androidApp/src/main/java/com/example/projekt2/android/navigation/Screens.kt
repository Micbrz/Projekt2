package com.example.projekt2.android.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.BlendMode.Companion.Screen

sealed class Screenss(val route: String){
    object SignUp: Screenss("login_screen")
    object Home: Screenss("Home_screen")
    object SignIn: Screenss("Registration_screen")
}

object PostOfficeAppRouter {

    var currentScreen: MutableState<Screenss> = mutableStateOf(Screenss.SignUp)

    fun navigateTo(destination : Screenss){
        currentScreen.value = destination
    }


}