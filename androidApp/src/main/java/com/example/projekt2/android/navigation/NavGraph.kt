package com.example.projekt2.android.navigation

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.projekt2.android.Data.FirebaseDatabaseManager
import com.example.projekt2.android.screens.AddPost
import com.example.projekt2.android.screens.ChatApplication
import com.example.projekt2.android.screens.HomeScreen
import com.example.projekt2.android.screens.Profile
import com.example.projekt2.android.screens.SignIn
import com.example.projekt2.android.screens.SignUp
import com.google.firebase.database.DatabaseReference


@Composable
fun NavGraph (context: Context, databaseReference: DatabaseReference,firebaseDatabaseManager: FirebaseDatabaseManager){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        Crossfade(targetState = PostOfficeAppRouter.currentScreen, label = ""){ currentState->
            when(currentState.value){
                is Screens.SignUp ->{
                    SignUp()
                }
                is Screens.SignIn ->{
                    SignIn()
                }
                is Screens.HomeScreen ->{
                    HomeScreen()
                }
                is Screens.Profile ->{

                    Profile(context, databaseReference,firebaseDatabaseManager)
                }
                is Screens.ChatApplication ->{
                    ChatApplication()
                }
                is Screens.AddPost ->{
                    AddPost()
                }
            }
        }
    }
}
