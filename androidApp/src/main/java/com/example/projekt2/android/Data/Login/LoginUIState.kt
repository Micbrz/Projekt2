package com.example.projekt2.android.Data.Login


data class LoginUIState(
    var email : String = "",
    var password : String = "",


    var emailError :Boolean = false,
    var passwordError :Boolean = false,
    )

