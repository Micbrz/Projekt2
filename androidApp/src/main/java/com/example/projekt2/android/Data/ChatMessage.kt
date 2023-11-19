package com.example.projekt2.android.Data

import com.google.firebase.firestore.PropertyName

data class ChatMessage(
    @get:PropertyName("senderId") @set:PropertyName("senderId")
    var senderId: String = "",
    @get:PropertyName("recipientId") @set:PropertyName("recipientId")
    var recipientId: String = "",
    @get:PropertyName("text") @set:PropertyName("text")
    var text: String = "",
    @get:PropertyName("timestamp") @set:PropertyName("timestamp")
    var timestamp: Long = 0
) {
    // Konstruktor bezargumentowy potrzebny do deserializacji Firebase
    constructor() : this("", "", "", 0)
}