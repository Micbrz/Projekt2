package com.example.projekt2.android.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.projekt2.android.Components.BackButton
import com.example.projekt2.android.Data.Models.UserModel
import com.example.projekt2.android.Data.ViewModel.UserViewModel
import com.example.projekt2.android.ItemView.ThreadItem
import com.example.projekt2.android.navigation.Routes
import com.example.projekt2.android.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainProfile(navHostController: NavHostController){

    val users = UserModel()
    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState(null)


    val followerList by userViewModel.followerList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)
    var currentUserId = ""

    if (FirebaseAuth.getInstance().currentUser != null){
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
    }
    if (currentUserId != ""){
        userViewModel.getFollowers(currentUserId)
        userViewModel.getFollowing(currentUserId)
    }

    val user = UserModel(
        name = SharedPref.getName(context),
        lastname = SharedPref.getLastName(context),
        image = SharedPref.getImage(context),
        bio = SharedPref.getBio(context)
    )

    userViewModel.fetchThreads(FirebaseAuth.getInstance().currentUser!!.uid)
    LazyColumn(){

        item{

            Log.d("ID", "Current user ID: $currentUserId")
            Log.d("ID", "Current user ID: $user.name")
            Log.d("ID", "Current user ID: $user.id")

            BackButton(onClick = { navHostController.navigate(Routes.HomeScreen.routes) })
            ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)){

                val ( text,lastname, logo, Name, bio,followers,following) = createRefs()

                Text(
                    text = SharedPref.getName(context), style = TextStyle(
                        fontSize = 24.sp
                    ),modifier = Modifier.constrainAs(text){
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = SharedPref.getLastName(context), style = TextStyle(
                        fontSize = 24.sp
                    ),modifier = Modifier.constrainAs(lastname){
                        start.linkTo(text.end, margin = 8.dp)

                    }
                )

                Image(painter = rememberAsyncImagePainter(model = SharedPref.getImage(context)),contentDescription ="close",
                    modifier = Modifier.constrainAs(logo){

                        end.linkTo(parent.end)
                    }.size(42.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )


                Text(
                    text = SharedPref.getBio(context), style = TextStyle(
                        fontSize = 20.sp
                    ),modifier = Modifier.constrainAs(bio){
                        top.linkTo(text.bottom)
                        start.linkTo(parent.start)
                    }
                )
                Text(
                    text = "${followerList!!.size} Obserwujących", style = TextStyle(
                        fontSize = 20.sp
                    ),modifier = Modifier.constrainAs(followers){
                        top.linkTo(bio.bottom)
                        start.linkTo(parent.start)
                    }
                )
                Text(
                    text = "${followingList!!.size} Obserwowani", style = TextStyle(
                        fontSize = 20.sp
                    ),modifier = Modifier.constrainAs(following){
                        top.linkTo(followers.bottom)
                        start.linkTo(parent.start)
                    }
                )

            }
        }

        items(threads ?: emptyList()){ pair ->
            ThreadItem(thread = pair , users = user, userId = SharedPref.getName(context))

        }
    }



}