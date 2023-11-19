package com.example.projekt2.android.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.BlendMode.Companion.Screen

sealed class Screens{
    object SignUp: Screens()
    object HomeScreen: Screens()
    object SignIn: Screens()
    object Profile: Screens()
    object ChatApplication: Screens()
}


object PostOfficeAppRouter {

    var currentScreen: MutableState<Screens> = mutableStateOf(Screens.SignUp)

    fun navigateTo(destination : Screens){
        currentScreen.value = destination
    }


}