package com.example.projekt2.android.Data.Registration


data class SignUpUIState(
    var email : String = "",
    var password : String = "",
    var name: String = "",
    var lastname: String = "",
    var bio:String = "",
    var imageUri: String = "",


    var emailError :Boolean = false,
    var passwordError :Boolean = false,
    var nameError :Boolean = false,
    var lastnameError :Boolean = false
)




