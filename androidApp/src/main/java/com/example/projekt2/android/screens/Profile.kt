package com.example.projekt2.android.screens


import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.projekt2.android.Components.BackButton
import com.example.projekt2.android.Data.FirebaseDatabaseManager
import com.example.projekt2.android.Data.UserModel
import com.example.projekt2.android.Data.UsersObj
import com.example.projekt2.android.R
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens
import com.example.projekt2.android.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(context: Context, databaseReference: DatabaseReference,firebaseDatabaseManager: FirebaseDatabaseManager) {
    /*val notification = rememberSaveable{mutableStateOf("")}
    if(notification.value.isNotEmpty()){
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value=""
    }*/


    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    val context = LocalContext.current

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {

            } else {

            }
        }
    var imageUri by remember { mutableStateOf<Uri?>(null)}
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else Manifest.permission.READ_EXTERNAL_STORAGE

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
            .padding(5.dp)
    ) {
        /*
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = "Cofnij",
                modifier = Modifier.clickable{ notification.value = "Usuniety"})
            Text(text = "Zapisz",
                modifier = Modifier.clickable{notification.value = "Zapisane"})
        }*/
        BackButton(onClick = { PostOfficeAppRouter.navigateTo(Screens.HomeScreen) })

        //zdjecie

        /*
        val painter = rememberAsyncImagePainter(
            if(imageUrl.value.isEmpty()){
                R.drawable.ic_user
            }
            else{
                imageUrl.value
            }
        )*/




        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Image(
                painter = if (imageUri == null) painterResource(id = R.drawable.ic_user)
                else rememberAsyncImagePainter(model = imageUri), contentDescription = "person",
                modifier = Modifier
                    .size(96.dp).clip(CircleShape).background(Color.LightGray)
                    .clickable {
                        val isGranted = ContextCompat.checkSelfPermission(
                            context,
                            permissionToRequest
                        ) == PackageManager.PERMISSION_GRANTED
                        if (isGranted) {
                            launcher.launch("image/*")

                        } else {
                            permissionLauncher.launch(permissionToRequest)

                        }
                    },
                contentScale = ContentScale.Crop
            )
        }
        Text(text = "Zmień zdjęcie profilowe")

    //zdjecie
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(text = "Imie", modifier = Modifier.width(100.dp))
        TextField(
            value = name, onValueChange = { name = it },
            placeholder = { Text(text = "Imie") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedTextColor = Color.Black
            )

        )
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(text = "Nazwisko", modifier = Modifier.width(100.dp))
        TextField(
            value = lastname, onValueChange = { lastname = it },
            placeholder = { Text(text = "Nazwisko") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedTextColor = Color.Black
            )

        )
    }
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp))
    {
        Text(text = "Opis:", modifier = Modifier.width(100.dp))
        TextField(
            value = bio, onValueChange = { bio = it },
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
                    var imageUrl = imageUri?.toString()
                    // Obsłuż odczytane dane użytkownika
                    name = userData.Name
                    lastname = userData.LastName
                    bio = userData.Bio
                    val pictureUrl = userData.Picture // Zakładając, że userData.Picture zawiera adres URL jako String
                    imageUri = if (!pictureUrl.isNullOrEmpty()) Uri.parse(pictureUrl) else null
                    imageUrl = userData.Picture
                    Log.d(TAG,"Imie : ${name}")
                    Log.d(TAG,"Nazwisko : ${lastname}")
                },
                onError = { error ->
                    Log.w(TAG, "Failed to read user data.", error.toException())
                    Toast.makeText(context, "Failed to read user data", Toast.LENGTH_SHORT).show()
                }
            )
        }

    Button(
        onClick = {
            val imageUrl = imageUri?.toString()

            val currentUser = FirebaseAuth.getInstance().currentUser
            val uid = currentUser?.uid ?: ""
            var empObj = UsersObj(uid, name, lastname, bio, imageUrl ?: "")


            Log.d(TAG, "Value is: " + empObj)
            Log.d(TAG, "Value is: " + databaseReference)
            if (empObj != null) {
                firebaseDatabaseManager.saveUserData(empObj)
            }


            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (empObj != null) {
                        firebaseDatabaseManager.saveUserData(empObj)
                    }
                    Log.d(TAG, "Value is: " + empObj)
                    //Toast.makeText(context, "Data added to firebase", Toast.LENGTH_SHORT).show()
                    imageUri?.let { uri ->
                        saveImage(name,lastname,bio, uri,uid) }
                }

                private val storageRef = Firebase.storage.reference
                private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

                private fun saveImage(
                    name: String,
                    lastname: String,
                    bio: String,
                    imageUri: Uri,
                    uid: String?
                ) {
                    val uploadTask = imageRef.putFile(imageUri)
                    uploadTask.addOnSuccessListener { _ ->
                        imageRef.downloadUrl.addOnSuccessListener {downloadUri ->
                            saveData(name, lastname, bio, downloadUri.toString(), uid)
                            //Toast.makeText(context,"Udało się zaladować zdjecie do savedata",Toast.LENGTH_LONG).show()
                        }.addOnFailureListener { exception ->
                            //Toast.makeText(context,"Nie udało się zaladować zdjecie do savedata",Toast.LENGTH_LONG).show()

                        }
                    }.addOnFailureListener { exception ->
                        // Obsługa błędu podczas przesyłania zdjęcia do Firebase Storage
                    }
                }

                @SuppressLint("SuspiciousIndentation")
                private fun saveData(
                    name: String,
                    lastname: String,
                    bio: String,
                    imageUrl: String,
                    uid: String?

                ) {
                    val userData = UserModel(name,lastname,bio,imageUrl)

                    val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")
                    val usersRef = database.getReference("users")

                            //tutaj jeszcze dziala
                    usersRef.child(uid!!).setValue(userData)
                        .addOnSuccessListener {
                            SharedPref.storeData(name,lastname,bio, imageUrl,context)
                            Toast.makeText(context,"zaladowano do sharedpref",Toast.LENGTH_LONG).show()
                        }.addOnFailureListener { exception ->
                            Toast.makeText(context,"Nie zaladowano do sharedpref",Toast.LENGTH_LONG).show()
                        }


                }


                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                    Toast.makeText(context, "Fail to add data $error", Toast.LENGTH_SHORT).show()
                }
            })
        }, modifier = Modifier.fillMaxWidth().padding(15.dp)
    ) {
        Text(text = "add firebase", modifier = Modifier.padding(5.dp))
    }
}
}




//
@Preview
@Composable
fun show(){


    val database = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")
    val databaseReference = database.getReference("EmployeeInfo");
    val firebaseDatabaseManager = FirebaseDatabaseManager()
    Profile(LocalContext.current, databaseReference,firebaseDatabaseManager)
}

