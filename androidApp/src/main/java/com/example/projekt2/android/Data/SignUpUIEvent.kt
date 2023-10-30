package com.example.projekt2.android.Data

sealed class SignUpUIEvent{
    data class EmailChanged(val email:String) : SignUpUIEvent()
    data class PasswordChanged(val password:String) : SignUpUIEvent()
    data class RepeatPasswordChanged(val repeatpassword:String) : SignUpUIEvent()
    object RegisterButtonClicked : SignUpUIEvent()
}
