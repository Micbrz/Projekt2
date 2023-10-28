package com.example.projekt2.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projekt2.android.Components.HomeScreenn
import com.example.projekt2.android.navigation.NavGraph
import com.example.projekt2.android.navigation.Screenss

import com.example.projekt2.android.screens.HomeScreen
import com.example.projekt2.android.screens.SignIn
import com.example.projekt2.android.screens.SignUp
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),

                ) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screenss.SignIn.route)
                    {
                        composable(route = Screenss.SignUp.route){
                            SignUp(navController)
                        }
                        composable(route = Screenss.Home.route){
                            HomeScreen(navController)
                        }
                        composable(route = Screenss.SignIn.route){
                            SignIn(navController)
                        }
                    }


                }
            }
    }
    /*
    fun createUserInFirebase(email:String, password:String){

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                Log.d(TAG,"isSuccessful = ${it.isSuccessful}")
            }.addOnFailureListener{
                Log.d(TAG,"isFailure = ${it.message}")
            }
    }*/

}
@Preview
@Composable
fun first(){
    MainActivity()
}







