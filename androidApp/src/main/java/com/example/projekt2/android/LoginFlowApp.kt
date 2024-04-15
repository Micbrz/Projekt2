package com.example.projekt2.android

import android.app.Application
import com.google.firebase.FirebaseApp

class LoginFlowApp: Application() {
    override fun onCreate(){
        super.onCreate()
        FirebaseApp.initializeApp(this)
        /*
        Firebase.initialize(context = this)
        Firebase.appCheck.installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance(),
        )*/
        /*val options = FirebaseOptions.Builder()
            .setApplicationId("CloneFBAndroidIos")
            .setDatabaseUrl("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")
            // ... inne ustawienia Firebase, jeśli są potrzebne
            .build()

        FirebaseApp.initializeApp(this,options)*/
    }
}