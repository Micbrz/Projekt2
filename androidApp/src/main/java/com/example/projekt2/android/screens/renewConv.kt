package com.example.projekt2.android.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projekt2.android.Data.ViewModel.ChatViewModel



@Composable
fun renewConv(navController: NavController,
              currentUserId: String,
              otherUserId: String?,
              ){
    val viewModel: ChatViewModel = viewModel()
    val messages by viewModel.messagesFlow.collectAsState(initial = emptyList())
    val messageToSend = remember { mutableStateOf("") }



    if (otherUserId != null) {
        LaunchedEffect(true) {
            viewModel.setUsersIds(currentUserId, otherUserId)
        }

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f), reverseLayout = false) {
                items(messages) { message ->
                    val isCurrentUserMessage = message.senderId == currentUserId
                    println("Sender ID: ${message.senderId}, Current User ID: $currentUserId")
                    println("Is Current User Message: $isCurrentUserMessage")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),

                        horizontalArrangement = if (message.otherUserId == currentUserId) Arrangement.End else Arrangement.Start

                    ) {
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