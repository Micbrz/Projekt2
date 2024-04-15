package com.example.projekt2.android.Data.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConversationsViewModel : ViewModel() {
    val conversationsFlow: StateFlow<List<String>> get() = _conversationsFlow.asStateFlow()
    private val _conversationsFlow = MutableStateFlow<List<String>>(emptyList())
    val db = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")

    init {
        Log.d("ConversationsViewModel", "ViewModel initialized")
        // Tu inicjalizujesz swojego DatabaseReference dla "messages"
        val messagesReference = db.reference.child("messages")

        // Dodajesz nasłuchiwanie dla zmian w "messages"
        val conversationsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Pobierasz aktualnego użytkownika
                val user = FirebaseAuth.getInstance().currentUser
                val currentUserId = user?.uid
                Log.d("ConversationsViewModel", "CurrentUserId: $currentUserId")

                val conversations = mutableListOf<String>()

                for (conversationSnapshot in snapshot.children) {
                    Log.d("ConversationsViewModel", "Snapshot: $conversationSnapshot")
                    // Pobierasz listę uczestników z danego snapshota
                    val users = conversationSnapshot.children.mapNotNull { it.child("otherUserId").getValue(String::class.java) }

                    // Jeśli lista uczestników nie jest pusta i nie zawiera aktualnego użytkownika,
                    // dodajesz do listy rozmów
                    if (users.isNotEmpty() && !users.contains(currentUserId)) {
                        conversations.add(users.joinToString())
                    }
                }

                // Ustawiasz nową listę rozmów
                _conversationsFlow.value = conversations
            }

            override fun onCancelled(error: DatabaseError) {
                // Obsługa błędów
            }
        }

        // Dodajesz listener do DatabaseReference
        messagesReference.addValueEventListener(conversationsListener)
    }
    fun loadConversations(conversations: List<String>) {
        _conversationsFlow.value = conversations
    }
}