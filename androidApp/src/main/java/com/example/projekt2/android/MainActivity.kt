package com.example.projekt2.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.projekt2.android.Data.UserData.FirebaseDatabaseManager
import com.example.projekt2.android.Data.ViewModel.ChatViewModel
import com.example.projekt2.android.navigation.NavGraph
import com.google.firebase.database.FirebaseDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firebaseDatabaseManager = FirebaseDatabaseManager()
        val database = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")
        val databaseReference = database.getReference("EmployeeInfo")


        setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),

                ) {
                    val chatViewModel = viewModel<ChatViewModel>()
                    val navController = rememberNavController()
                    NavGraph(navController,chatViewModel)
                }

            }
    }


}








