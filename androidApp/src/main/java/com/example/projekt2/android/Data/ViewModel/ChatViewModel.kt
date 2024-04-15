package com.example.projekt2.android.Data.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.projekt2.android.Data.Models.Notification
import com.example.projekt2.android.Data.Models.UserModel
import com.example.projekt2.android.Data.NotificationManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class Message(
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    var senderId: String="",
    var otherUserId: String = ""
)
class ChatViewModel : ViewModel() {
    private val _usersFlow = MutableStateFlow<List<UserModel>>(emptyList())
    val usersFlow: StateFlow<List<UserModel>> get() = _usersFlow.asStateFlow()

    // Metoda do aktualizowania listy użytkowników
    fun updateUsers(users: List<UserModel>) {
        _usersFlow.value = users
    }
    val notificationManager = NotificationManager()
    var currentUserId by mutableStateOf("")
    var otherUserId by mutableStateOf("")
    val db =
        FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")
    private val _messagesFlow = MutableStateFlow<List<Message>>(emptyList())
    val messagesFlow: StateFlow<List<Message>> get() = _messagesFlow.asStateFlow()

    init {
        _messagesFlow.value = emptyList() // Na początku ustawiamy pustą listę
    }
    private fun getLastMessageTimestamp(): Long {
        return _messagesFlow.value.lastOrNull()?.timestamp ?: 0L
    }
    fun setUsersIds(currentId: String, otherId: String) {
        currentUserId = currentId
        otherUserId = otherId

        Log.d("ChatViewModel", "Current user ID: $currentUserId")
        Log.d("ChatViewModel", "Other user ID: $otherUserId")

        // Łączymy identyfikatory użytkowników, tak aby uzyskać unikalny identyfikator dla konwersacji
        val conversationId = getConversationId(currentUserId, otherUserId)
        Log.d("ChatViewModel", "Conversation ID: $conversationId")
        val messagesQuery = db.reference.child("messages").child(conversationId)

        val messageListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                message?.let {
                    val senderId = it.senderId
                    val receiverId = if (senderId == currentId) otherId else currentId

                    val updatedList = _messagesFlow.value.toMutableList()
                    updatedList.add(it.copy(senderId = senderId, otherUserId = receiverId))
                    _messagesFlow.value = updatedList.sortedBy { it.timestamp }

                    if (it.timestamp > getLastMessageTimestamp() && otherUserId != currentUserId) {
                        val senderName = "Someone"
                        val notificationContent = "$senderName sent a new message"
                        notificationManager.addNotification(Notification("New Message", notificationContent))
                    }
                }
            }


            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            // pozostałe metody w listenerze...

            override fun onCancelled(error: DatabaseError) {
                // Obsługa błędów
            }
        }

        messagesQuery.addChildEventListener(messageListener)

    }

    fun sendMessage(currentId: String, otherId: String,messageText: String) {
        val conversationId = getConversationId(currentUserId, otherUserId)
        val query = db.reference.child("messages").child(conversationId).push()
        val message = Message(messageText, System.currentTimeMillis())
        message.senderId = currentUserId
        message.otherUserId = otherUserId
        query.setValue(message)
    }

    // Tworzymy unikalny identyfikator dla konwersacji na podstawie identyfikatorów użytkowników
    private fun getConversationId(userId1: String, userId2: String): String {
        return if (userId1 < userId2) "$userId1-$userId2" else "$userId2-$userId1"
    }
}

