package com.example.projekt2.android.Data.Login

sealed class LoginUIEvent{
    data class EmailChanged(val email:String) : LoginUIEvent()
    data class PasswordChanged(val password:String) : LoginUIEvent()


    object LoginButtonClicked : LoginUIEvent()
}
