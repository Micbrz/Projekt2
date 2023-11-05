package com.example.projekt2.android.Data

sealed class LoginUIEvent{
    data class EmailChanged(val email:String) : LoginUIEvent()
    data class PasswordChanged(val password:String) : LoginUIEvent()


    object LoginButtonClicked : LoginUIEvent()
}
