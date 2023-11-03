package com.example.projekt2.android.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projekt2.android.screens.HomeScreen
import com.example.projekt2.android.screens.Profile
import com.example.projekt2.android.screens.SignIn
import com.example.projekt2.android.screens.SignUp


@Composable
fun NavGraph (){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        Crossfade(targetState = PostOfficeAppRouter.currentScreen){currentState->
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
                    Profile()
                }
            }
        }
    }
}
