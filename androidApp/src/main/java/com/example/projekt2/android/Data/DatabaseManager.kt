import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.projekt2.android.Data.ChatMessage
import com.google.firebase.database.ValueEventListener
/*
class DatabaseManager {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("messages")

    fun sendMessage(chatMessage: ChatMessage) {
        // Wysyłanie wiadomości do bazy danych
        databaseReference.push().setValue(chatMessage)
    }

    fun addMessageListener(listener: ValueEventListener) {
        // Dodawanie nasłuchiwania na wiadomości
        databaseReference.addValueEventListener(listener)
    }

    // Inne metody obsługujące bazę danych
}*/