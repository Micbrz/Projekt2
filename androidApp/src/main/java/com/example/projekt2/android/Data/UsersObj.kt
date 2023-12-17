package com.example.projekt2.android.Data

data class UsersObj(
    var uid: String = "",
    var Name: String = "",
    var LastName: String = "",
    var Bio: String = "",
    var Picture: String = "",

    ){


    constructor() : this("","","","","")
}
