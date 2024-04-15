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
import com.example.projekt2.android.Data.ViewModel.ChatViewModel
import com.example.projekt2.android.screens.AddPost
import com.example.projekt2.android.screens.ChatApplication
import com.example.projekt2.android.screens.ConversationsScreen
import com.example.projekt2.android.screens.ForgotPassword
import com.example.projekt2.android.screens.HomeScreen
import com.example.projekt2.android.screens.MainProfile
import com.example.projekt2.android.screens.NotificationScreen
import com.example.projekt2.android.screens.OtherUser
import com.example.projekt2.android.screens.Profile
import com.example.projekt2.android.screens.Search
import com.example.projekt2.android.screens.SignIn
import com.example.projekt2.android.screens.SignUp
import com.example.projekt2.android.screens.renewConv


@Composable
fun NavGraph2(navController : NavHostController){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        Crossfade(targetState = PostOfficeAppRouter.currentScreen, label = ""){ currentState->
            when(currentState.value){

                is Screens.HomeScreen ->{
                    HomeScreen(navController)
                }
                is Screens.SignIn ->{
                    SignIn(navController)
                }


                else -> {

                }
            }
        }
    }


}

@Composable
fun NavGraph(navController: NavHostController,chatViewModel: ChatViewModel){
    NavHost(navController = navController,
        startDestination = Routes.SignIn.routes){
        composable(Routes.OtherUser.routes){
            val data = it.arguments!!.getString("data")
            OtherUser(navController,data!!,chatViewModel)
        }
        composable(Routes.MainProfile.routes){
            MainProfile(navController)
        }
        composable(Routes.HomeScreen.routes){
            HomeScreen(navController)
        }
        composable(Routes.AddPost.routes){
            AddPost(navController)
        }
        composable(Routes.Profile.routes){
            Profile(navController)
        }
        composable(Routes.ForgotPassword.routes){
            ForgotPassword(navController)
        }
        composable(Routes.SignIn.routes){
            SignIn(navController)
        }
        composable(Routes.SignUp.routes){
            SignUp(navController)
        }
        composable(Routes.Search.routes){
            Search(navController)
        }
        composable(Routes.ConversationsScreen.routes){
            ConversationsScreen(navController)
        }

        composable(Routes.renewConv.routes){

            renewConv(navController,chatViewModel.currentUserId, chatViewModel.otherUserId)
        }


        composable(Routes.ChatApplication.routes){
            ChatApplication(navController,chatViewModel.currentUserId, chatViewModel.otherUserId
            )
        }

        composable(Routes.NotificationScreen.routes){
            val notifications = chatViewModel.notificationManager.notifications.value
            NotificationScreen(navController,notifications)
        }

    }

}
