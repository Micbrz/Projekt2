package com.example.projekt2.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.projekt2.android.Data.FirebaseDatabaseManager
import com.example.projekt2.android.navigation.NavGraph

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firebaseDatabaseManager = FirebaseDatabaseManager()
        val database = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")
        val databaseReference = database.getReference("EmployeeInfo");

        setContent {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),

                ) {
                    NavGraph(LocalContext.current, databaseReference,firebaseDatabaseManager )
                }

            }
    }


}
/*
@Preview
@Composable
fun first(){

    val firebaseDatabase = FirebaseDatabase.getInstance();
    val databaseReference = firebaseDatabase.getReference("EmployeeInfo");
    NavGraph(LocalContext.current, databaseReference)
}*/







