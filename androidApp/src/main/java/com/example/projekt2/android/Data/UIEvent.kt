package com.example.projekt2.android.Data

sealed class UIEvent{
    data class EmailChanged(val email:String) : UIEvent()
    data class PasswordChanged(val password:String) : UIEvent()
    data class RepeatPasswordChanged(val repeatpassword:String) : UIEvent()
    object RegisterButtonClicked : UIEvent()
}
