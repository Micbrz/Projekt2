package com.example.projekt2.android.Data

import androidx.compose.runtime.mutableStateOf
import com.example.projekt2.android.Data.Models.Notification


class NotificationManager {
    val notifications = mutableStateOf<List<Notification>>(emptyList())

    fun addNotification(notification: Notification) {
        notifications.value += notification
    }
}