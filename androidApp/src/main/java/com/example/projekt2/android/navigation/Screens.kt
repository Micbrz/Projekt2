package com.example.projekt2.android.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


sealed class Screens{
    object SignUp: Screens()
    object HomeScreen: Screens()
    object SignIn: Screens()
    object Profile: Screens()
    object ChatApplication: Screens()
    object AddPost: Screens()
    object Search: Screens()
    object MainProfile: Screens()

}

sealed class Routes(val routes:String){
    object OtherUser: Routes("other_user/{data}")
    object SignUp: Routes("signup")
    object HomeScreen: Routes("home")
    object SignIn: Routes("signin")
    object Profile: Routes("profile")
    object ChatApplication: Routes("chat/{uid}")
    object AddPost: Routes("addpost")
    object Search: Routes("search")
    object MainProfile: Routes("mainprofile")
    object NotificationScreen: Routes("notificationscreen")
    object ForgotPassword: Routes("forgotpassword")
    object ConversationsScreen: Routes("conversationscreen")
    object renewConv: Routes("renewconv")
}


object PostOfficeAppRouter {

    var currentScreen: MutableState<Screens> = mutableStateOf(Screens.SignIn)

    fun navigateTo(destination : Screens){
        currentScreen.value = destination
    }


}