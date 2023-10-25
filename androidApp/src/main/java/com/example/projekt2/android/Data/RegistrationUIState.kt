package com.example.projekt2.android.Data




data class RegistrationUIState(
    var email : String = "",
    var password : String = "",
    var repeatpassword: String = "",

    var emailError :Boolean = false,
    var passwordError :Boolean = false,
    var repeatpasswordError :Boolean = false
)




