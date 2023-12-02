package com.example.projekt2.android.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projekt2.android.Components.RegistrationButton
import com.example.projekt2.android.Data.Registration.SignUpViewModel
import com.example.projekt2.android.Data.SearchViewModel
import com.example.projekt2.android.Data.UsersObj
import com.example.projekt2.android.R
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//wyszukiwarka profili

fun searchUsers(query: String, onUsersFetched: (List<UsersObj>) -> Unit) {
    val usersList = mutableListOf<UsersObj>()

    val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")

    usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (userSnapshot in snapshot.children) {
                val id = userSnapshot.key ?: ""
                val name = userSnapshot.child("Name").getValue(String::class.java) ?: ""
                val lastName = userSnapshot.child("LastName").getValue(String::class.java) ?: ""
                val bio = userSnapshot.child("Bio").getValue(String::class.java) ?: ""
                val picture = userSnapshot.child("Picture").getValue(String::class.java) ?: ""
                val user = UsersObj(id, name, lastName,bio,picture)
                usersList.add(user)
            }

            // Filtruj użytkowników na podstawie zapytania
            val filteredUsers = usersList.filter { user ->
                user.Name.contains(query, ignoreCase = true) || user.LastName.contains(query, ignoreCase = true)
            }

            // Wywołanie funkcji callback z wynikami wyszukiwania
            onUsersFetched(filteredUsers)
        }

        override fun onCancelled(error: DatabaseError) {
            // Obsługa błędu
        }
    })
}


// tutaj będą pojawiać się posty znajomych
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(loginViewModel: SignUpViewModel = viewModel(), viewModel: SearchViewModel = viewModel()) {
    val context = LocalContext.current
    var expandedMenu by remember {mutableStateOf (false)}
    var searchResult by remember { mutableStateOf(emptyList<UsersObj>()) }
    /*
            SearchBar(query=,
                onQueryChange= ,
                onSearch=,
                active=,
                onActiveChange=)*/
    Scaffold(

        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.Cyan,
                ), //kolor tła paska narzędziowego
                title = { LogoImage() },
                navigationIcon = {
                    IconButton(onClick = { expandedMenu = !expandedMenu }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { PostOfficeAppRouter.navigateTo(Screens.AddPost) }) {
                        Icon(Icons.Default.Add, contentDescription = "More")
                    }
                },


            )

        }

    ){
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ){

            DropdownMenu(
                expanded = expandedMenu,
                onDismissRequest = { expandedMenu = false },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                DropdownMenuItem(text ={Text("Wybierz opcje")},
                    onClick = {

                        Toast.makeText(context,"wybrano opcje: ",Toast.LENGTH_LONG)
                    })

                RegistrationButton(
                    value = stringResource(id=R.string.Profile),
                    onButtonClicked={
                        PostOfficeAppRouter.navigateTo(Screens.Profile)
                    },
                    isEnabled = true
                )
                RegistrationButton(
                    value = stringResource(id= R.string.logout),
                    onButtonClicked = {
                        loginViewModel.logout()
                        PostOfficeAppRouter.navigateTo(Screens.SignIn)
                    },
                    isEnabled = true
                )
                RegistrationButton(
                    value = stringResource(id=R.string.Chat),
                    onButtonClicked = {
                        PostOfficeAppRouter.navigateTo(Screens.ChatApplication)
                    },
                    isEnabled=true
                )


            }





    }
}}
@Composable
fun LogoImage() {
    // Tutaj użyj odpowiedniego obrazka Twojego logo
    val painter: Painter = painterResource(id = R.drawable.ic_user) // Zmodyfikuj, aby załadować swoje logo
    Image(
        painter = painter,
        contentDescription = "Logo"
    )
}



