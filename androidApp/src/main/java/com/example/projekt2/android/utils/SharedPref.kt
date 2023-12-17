package com.example.projekt2.android.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE


object SharedPref {

    fun storeData(
        name: String,
        lastname:String, bio:String, imageUrl:String,
        context: Context){
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Name", name)
        editor.putString("LastName", lastname)
        editor.putString("Bio", bio)
        editor.putString("Picture",imageUrl)
        editor.apply()
    }

    fun getName(context:Context): String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("Name","")!!
    }
    fun getLastName(context:Context): String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("LastName","")!!
    }
    fun getBio(context:Context): String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("Bio","")!!
    }
    fun getImage(context:Context): String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("Picture","")!!
    }
}