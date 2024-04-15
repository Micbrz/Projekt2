package com.example.projekt2.android.Data.Models

data class UserModel(
    val name: String = "",
    val lastname: String = "",
    val bio: String = "",
    val image: String = "",
    var uid: String = "",
){
    constructor() : this("","","","","")
}




