package com.example.projekt2.android.screens

import androidx.compose.runtime.Composable
import com.example.projekt2.android.Components.BackButton
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens

@Composable
fun AddPost(){
    BackButton(onClick = { PostOfficeAppRouter.navigateTo(Screens.HomeScreen)})
}