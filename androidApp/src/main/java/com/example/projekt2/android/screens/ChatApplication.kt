package com.example.projekt2.android.screens


import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projekt2.android.Components.BackButton
import com.example.projekt2.android.Data.ChatMessage
import com.example.projekt2.android.Data.FirebaseDatabaseManager
import com.example.projekt2.android.Data.UsersObj
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


fun addMessage(messageText: String, recipientId: String,senderId: String, senderUsername: String){


    var message = ChatMessage(
        senderId = senderId,
        text = messageText,
        recipientId = recipientId,
        timestamp = System.currentTimeMillis())
    var messageCollection = FirebaseFirestore.getInstance().collection("messages")
    messageCollection.add(message)
        .addOnSuccessListener{documentReference ->

        }
        .addOnFailureListener{
            Log.d(ContentValues.TAG,"Inside_ChatMessage ${it.localizedMessage}")
        }
}
fun getMessagesForUser(
    recipientId: String,
    onMessagesReady: (List<ChatMessage>) -> Unit,
    onError: (Exception) -> Unit) {
    var messagesRef = FirebaseFirestore.getInstance().collection("messages")

    // Get messages for the specific recipient
    messagesRef.whereEqualTo("recipientId", recipientId)
        .get()
        .addOnSuccessListener { documents ->
            var messagesList = mutableListOf<ChatMessage>()
            for (document in documents) {
                val message = document.toObject(ChatMessage::class.java)
                messagesList.add(message)
            }
            onMessagesReady(messagesList)
        }
        .addOnFailureListener { e ->
            onError(e)
        }
}
fun getMessagesForUserV1(
    recipientId: String,
    onMessagesReady: (List<ChatMessage>) -> Unit,
    onError: (Exception) -> Unit
) {
    val messagesRef = FirebaseFirestore.getInstance().collection("messages")
    messagesRef.whereEqualTo("recipientId", recipientId)
        .get()
        .addOnSuccessListener { documents ->
            var messagesList = mutableListOf<ChatMessage>()
            for (document in documents) {
                val message = document.toObject(ChatMessage::class.java)
                messagesList.add(message)
            }
            onMessagesReady(messagesList)
        }
        .addOnFailureListener { e ->
            onError(e)
        }
}

fun getMessagesForUserV2(
    recipientId: String,
    onMessagesReady: (List<ChatMessage>) -> Unit,
    onError: (Exception) -> Unit
) {
    val messagesRef = FirebaseFirestore.getInstance().collection("messages")
    messagesRef.whereEqualTo("senderId", recipientId)
        .get()
        .addOnSuccessListener { documents ->
            var messagesList = mutableListOf<ChatMessage>()
            for (document in documents) {
                val message = document.toObject(ChatMessage::class.java)
                messagesList.add(message)
            }
            onMessagesReady(messagesList)
        }
        .addOnFailureListener { e ->
            onError(e)
        }
}

@Composable
fun ChatApplication() {
    var messageText by remember {mutableStateOf("")}
    var usersList by remember {mutableStateOf<List<UsersObj>>(emptyList())}
    var selectedUser: UsersObj? by remember { mutableStateOf(null)}
    val currentUser = FirebaseAuth.getInstance().currentUser // Pobranie bieżącego użytkownika
    val currentUserId = currentUser?.uid ?: "" // Pobranie ID bieżącego użytkownika
    var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }

    LaunchedEffect(Unit){
        FirebaseDatabaseManager().getUserList(
            onUsersListReady = { users ->
                usersList = users
                usersList.forEach{user ->
                    Log.d("UserList ${user.Name}","User ID ${user.uid}")
                }
            },
            onError = { error ->
                Log.e("UserList","Error fetching user list: $error")

            }
        )
    }
    LaunchedEffect(selectedUser) {
        selectedUser?.let { user ->
            val recipientId = user.uid
            val name = user.Name
            getMessagesForUser(recipientId,
                onMessagesReady = { receiveMessages ->

                    messages = messages + receiveMessages



                },
                onError = { error ->
                    Log.e(ContentValues.TAG, "Error fetching messages: ${error.message}")
                    // Obsługa błędu pobierania wiadomości
                }
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxSize()


        ) {BackButton(onClick = { PostOfficeAppRouter.navigateTo(Screens.HomeScreen)})
            Text("Lista użytkownikow")
            LazyColumn(

                modifier = Modifier.fillMaxWidth()
            ) {

                items(usersList) { user ->
                    UserItem(user = user, onUserSelected = { selectedUser = it })
                }
            }
            Text("koniec listy")
            Text("Wiadomosci:")
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(messages) { message ->
                    DisplayMessages(message,usersList)
                }
            }
            Text("Koniec wiadomosci")

            Column(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
            ) {

                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    label = { Text("Wpisz wiadomość") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        selectedUser?.let { user ->
                            val recipientId = user.uid
                            val senderUsername = currentUser?.displayName ?: "" // Pobranie nazwy nadawcy
                            addMessage(messageText,
                                recipientId,
                                currentUserId,
                                senderUsername)
                            messageText = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Wyślij")
                }


            }


        }

    }

}
@Composable
fun UserItem(user: UsersObj, onUserSelected: (UsersObj) -> Unit) {
    Text(
        text = "Imie: ${user.Name} Nazwisko: ${user.LastName}",
        modifier = Modifier.clickable {
            Log.d("UserItem", "Recipient Name: ${user.Name} Recipient ID: ${user.uid}")
            onUserSelected(user) }
    )
}
@Composable
fun DisplayMessages(message: ChatMessage,users: List<UsersObj>) {
    val senderUser = users.find { it.uid == message.senderId }
    val senderName = senderUser?.Name ?: "Unknown User"
    Text(text = "Wiadomość od $senderName: ${message.text}")


}






