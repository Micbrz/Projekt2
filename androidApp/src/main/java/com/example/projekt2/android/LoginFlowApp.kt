package com.example.projekt2.android

import android.app.Application
import com.google.firebase.FirebaseApp

class LoginFlowApp: Application() {
    override fun onCreate(){
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}