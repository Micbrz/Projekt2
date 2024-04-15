package com.example.projekt2.android.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projekt2.android.Data.ViewModel.ConversationsViewModel
import com.example.projekt2.android.navigation.Routes

@Composable
fun ConversationsScreen(navController: NavController) {
    val viewModel: ConversationsViewModel = viewModel()

    val conversations by viewModel.conversationsFlow.collectAsState(initial = emptyList())



    LazyColumn {
        items(conversations) { otherUserId ->
            if (otherUserId.isNotBlank()) {
                ConversationItem(
                    otherUserId = otherUserId,
                    onItemClick = {
                        navController.navigate("${Routes.ChatApplication.routes}/$otherUserId")
                    }
                )
            }
        }
    }
}

@Composable
fun ConversationItem(otherUserId: String, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick()
                Log.d("ConversationItem", "Item clicked: $otherUserId")},
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = otherUserId)
    }
}