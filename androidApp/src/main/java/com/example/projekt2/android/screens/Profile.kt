package com.example.projekt2.android.screens


import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projekt2.android.R
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.example.projekt2.android.Components.BackButton

import com.example.projekt2.android.Data.FirebaseDatabaseManager
import com.example.projekt2.android.Data.UsersObj
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.*



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(context: Context, databaseReference: DatabaseReference,firebaseDatabaseManager: FirebaseDatabaseManager){
    /*val notification = rememberSaveable{mutableStateOf("")}
    if(notification.value.isNotEmpty()){
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value=""
    }*/


    var name = remember{mutableStateOf(TextFieldValue())}
    var lastname = remember{mutableStateOf(TextFieldValue())}
    var bio = remember{mutableStateOf(TextFieldValue())}

    Column(modifier = Modifier.verticalScroll(rememberScrollState())
        .padding(5.dp)
    ){
        /*
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = "Cofnij",
                modifier = Modifier.clickable{ notification.value = "Usuniety"})
            Text(text = "Zapisz",
                modifier = Modifier.clickable{notification.value = "Zapisane"})
        }*/
        BackButton(onClick = { PostOfficeAppRouter.navigateTo(Screens.HomeScreen)})

        //zdjecie
        val imageUrl = rememberSaveable{mutableStateOf("")}
        val painter = rememberAsyncImagePainter(
            if(imageUrl.value.isEmpty()){
                R.drawable.ic_user
            }
            else{
                imageUrl.value
            }
        )
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ){ uri: Uri? ->
            uri?.let {imageUrl.value = it.toString()

            }
        }
        Column(
            modifier=Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Card(shape = CircleShape,
                modifier = Modifier.padding(8.dp)
                    .size(100.dp)
            )
            {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable{ launcher.launch("image/*")},
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = "Zmień zdjęcie profilowe")
        }
        //zdjecie
        Row(modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(text = "Imie", modifier = Modifier.width(100.dp))
            TextField(value = name.value, onValueChange = {name.value = it},
                placeholder={Text(text="Imie")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Color.Black)

                )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically)
        {
            Text(text = "Nazwisko", modifier = Modifier.width(100.dp))
            TextField(value = lastname.value, onValueChange = {lastname.value = it},
                placeholder={Text(text="nazwisko")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Color.Black)

            )
        }
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp))
        {
            Text(text="Opis:", modifier=Modifier.width(100.dp))
            TextField(value = bio.value, onValueChange = {bio.value = it},
                placeholder = { Text(text = "Opis") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Color.Black
                ),
                singleLine = false,
                modifier = Modifier.height(160.dp)
                )
        }
        LaunchedEffect(Unit) {
            // Pobierz dane użytkownika z Firebase
            firebaseDatabaseManager.readUserData(
                onDataRead = { userData ->
                    // Obsłuż odczytane dane użytkownika
                    name.value = TextFieldValue(userData.Name)
                    lastname.value = TextFieldValue(userData.LastName)
                    bio.value = TextFieldValue(userData.Bio)
                    imageUrl.value = userData.Picture
                },
                onError = { error ->
                    Log.w(TAG, "Failed to read user data.", error.toException())
                    Toast.makeText(context, "Failed to read user data", Toast.LENGTH_SHORT).show()
                }
            )
        }
        Button(onClick = {
            val currentUser = FirebaseAuth.getInstance().currentUser
            val uid = currentUser?.uid ?: ""
            var empObj = UsersObj(uid,name.value.text,lastname.value.text,bio.value.text,imageUrl.value)
            Log.d(TAG, "Value is: " + empObj)
            Log.d(TAG, "Value is: " + databaseReference)


            databaseReference.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot){
                    firebaseDatabaseManager.saveUserData(empObj)
                    Log.d(TAG, "Value is: " + empObj)
                    Toast.makeText(context,"Data added to firebase",Toast.LENGTH_SHORT).show()
                }
                override fun onCancelled(error: DatabaseError){
                    Log.w(TAG, "Failed to read value.", error.toException())
                    Toast.makeText(context,"Fail to add data $error",Toast.LENGTH_SHORT).show()
                }
            })
        },modifier = Modifier.fillMaxWidth().padding(15.dp)
        ){
            Text(text="add firebase",modifier = Modifier.padding(5.dp))
        }
    }
}



@Preview
@Composable
fun show(){


    val database = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")
    val databaseReference = database.getReference("EmployeeInfo");
    val firebaseDatabaseManager = FirebaseDatabaseManager()
    Profile(LocalContext.current, databaseReference,firebaseDatabaseManager)
}

