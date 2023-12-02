package com.example.projekt2.android.Data.Registration

sealed class SignUpUIEvent{
    data class EmailChanged(val email:String) : SignUpUIEvent()
    data class PasswordChanged(val password:String) : SignUpUIEvent()
    data class NameChanged(val name:String) : SignUpUIEvent()
    data class LastNameChanged(val lastname:String) : SignUpUIEvent()
    object RegisterButtonClicked : SignUpUIEvent()
}
