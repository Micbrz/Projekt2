package com.example.projekt2.android.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE


object SharedPref {
    

    fun storeData(
        name: String,
        lastname:String, bio:String, image:String,uid:String,
        context: Context){
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("lastname", lastname)
        editor.putString("bio", bio)
        editor.putString("image",image)
        editor.apply()
    }

    fun getName(context:Context): String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("name","")!!
    }
    fun getLastName(context:Context): String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("lastname","")!!
    }
    fun getBio(context:Context): String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("bio","")!!
    }
    fun getImage(context:Context): String{
        val sharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("image","")!!
    }
}