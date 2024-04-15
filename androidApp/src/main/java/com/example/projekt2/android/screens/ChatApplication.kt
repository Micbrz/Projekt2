package com.example.projekt2.android.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.projekt2.android.Data.Models.UserModel
import com.example.projekt2.android.Data.ViewModel.ChatViewModel
import com.example.projekt2.android.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@Composable
fun ChatApplication(
    navController: NavController,
    currentUserId: String,
    otherUserId: String?,
    viewModel: ChatViewModel = viewModel()
) {

    //val viewModel: ChatViewModel = viewModel()
    val messages by viewModel.messagesFlow.collectAsState(initial = emptyList())
    val messageToSend = remember { mutableStateOf("") }
    val database = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")
    val usersRef = database.getReference("users")
    var otherUserImageUrl by remember { mutableStateOf<String?>(null) }
    var currentUserImageUrl by remember { mutableStateOf<String?>(null) }


    if (otherUserId != null) {
        LaunchedEffect(true) {
            viewModel.setUsersIds(currentUserId, otherUserId)
        }
        usersRef.child(currentUserId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentUser = dataSnapshot.getValue(UserModel::class.java)
                currentUserImageUrl = currentUser?.image
            }

            override fun onCancelled(error: DatabaseError) {
                // Obsłuż błąd pobierania danych
                Log.e("Firebase", "Error getting current user data", error.toException())
            }
        })
        usersRef.child(otherUserId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val otherUser = dataSnapshot.getValue(UserModel::class.java)
                otherUser?.let {
                    otherUserImageUrl = it.image
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Obsłuż błąd pobierania danych
                Log.e("Firebase", "Error getting other user data", error.toException())
            }
        })

/*
        LaunchedEffect(currentUserId, otherUserId) {
            // Pobierz zdjęcia użytkowników asynchronicznie
            viewModel.getUserImage(currentUserId) { currentUserImage ->
                viewModel.getUserImage(otherUserId) { otherUserImage ->
                    currentUserImage?.let { currentUserImage ->
                        otherUserImage?.let { otherUserImage ->
                            // Zaktualizuj stan z pobranymi obrazkami
                            setCurrentUserImage(currentUserImage)
                            setOtherUserImage(otherUserImage)
                        }
                    }
                }
            }
        }*/

        val users by viewModel.usersFlow.collectAsState(initial = emptyList())
        val currentUser = users.find { it.uid == currentUserId }
        val otherUser = users.find { it.uid == otherUserId }


        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f), reverseLayout = false) {
                items(messages) { message ->
                    val isCurrentUserMessage = message.senderId == currentUserId
                    val author = users.find { it.uid == message.senderId }
                    val currentUserImage = currentUserImageUrl
                    val otherUserImage = otherUserImageUrl
                    Log.d("ImageLoading", "Current user image URL: $currentUserImage")
                    Log.d("ImageLoading", "Other user image URL: $otherUserImage")
                    Log.d("UserData", "current user ID: $currentUserId")
                    Log.d("UserData", "Other user ID: $otherUserId")
                    Log.d("UserData", "Other user ID: $message.otherUserId")

                    /*println("Sender ID:${user.toString} ${message.senderId}, Current User ID: $currentUserId")
                    println("Is Current User Message: $isCurrentUserMessage")*/
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),

                        horizontalArrangement = if (message.otherUserId == currentUserId) Arrangement.End else Arrangement.Start

                    ) {


                        Image(
                            painter = rememberImagePainter(
                                data = if (isCurrentUserMessage) currentUserImage else otherUserImage,
                                builder = {
                                    crossfade(true)
                                    placeholder(R.drawable.ic_user) // Dodanie domyślnego obrazka profilowego
                                    error(R.drawable.ic_user) // Dodanie domyślnego obrazka profilowego w przypadku błędu
                                }
                            ),
                            contentDescription = "User image",
                            modifier = Modifier.size(40.dp)
                        )

                        Text(
                            text = message.text,
                            modifier = Modifier

                                .padding(8.dp)
                                .background(Color(173, 216, 230), shape = RoundedCornerShape(15.dp))
                                .border(
                                    1.dp,
                                    Color(173, 216, 230),
                                    shape = RoundedCornerShape(15.dp)
                                )
                                .padding(8.dp)

                        )

                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = messageToSend.value,
                    onValueChange = { messageToSend.value = it },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        viewModel.sendMessage(currentUserId, otherUserId, messageToSend.value)
                        messageToSend.value = ""
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Wyślij")
                }
            }
        }
    }
}

data class User(
    val bio: String?,
    val lastname: String?,
    val name: String?,
    val imageUrl: String?,
    val uid: String?
)
