package com.example.projekt2.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.projekt2.android.Data.ViewModel.SearchViewModel
import com.example.projekt2.android.ItemView.UserItem


@Composable
fun Search(navHostController: NavHostController){
    val searchViewModel: SearchViewModel = viewModel()
    val userList by searchViewModel.userList.observeAsState(null)

    var search by remember {
        mutableStateOf("")
    }
    Column(){
        Text(
            text = "Szukaj", style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
            ),modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        OutlinedTextField(value = search,onValueChange = { search = it}, label ={
            Text(text = "Szukaj użytkownika")
        },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ), singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)


            }
            )

    LazyColumn(modifier = Modifier.padding(top = 48.dp)){
        if (userList != null && userList!!.isNotEmpty()) {

            val filterItems = userList!!.filter {val fullName = "${it.name} ${it.lastname}"
                it.name!!.contains(search, ignoreCase = false) || it.lastname!!.contains(search, ignoreCase = false) || fullName.contains(search, ignoreCase = false) }

            items(filterItems ?: emptyList()) { pairs ->
                UserItem(pairs,navHostController = navHostController)
            }
        }
    }
    }

}