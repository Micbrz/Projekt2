package com.example.projekt2.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.projekt2.android.Data.FirebaseDatabaseManager
import com.example.projekt2.android.navigation.NavGraph
import com.google.firebase.database.FirebaseDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firebaseDatabaseManager = FirebaseDatabaseManager()
        val database = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")
        val databaseReference = database.getReference("EmployeeInfo");

        setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),

                ) {
                    NavGraph(LocalContext.current, databaseReference,firebaseDatabaseManager )
                }

            }
    }


}








