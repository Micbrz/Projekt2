package com.example.projekt2.android.Data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseDatabaseManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")

    fun saveUserData(employeeObj: UsersObj){
        val user = auth.currentUser
        val uid = user?.uid
        val usersRef: DatabaseReference = database.getReference("users")

        if(uid != null){
            val userData = mapOf(
                "uid" to employeeObj.uid,
                "Name" to employeeObj.Name,
                "LastName" to employeeObj.LastName,
                "Bio" to employeeObj.Bio,
                "Picture" to employeeObj.Picture
            )
            val userKey = usersRef.push().key // generowanie unikalnego klucza użytkownika
            if (userKey != null){
                usersRef.child(uid).setValue(userData)
            }

        }
    }
    fun readUserData(onDataRead: (UsersObj) -> Unit, onError: (DatabaseError) -> Unit) {
        val user = auth.currentUser
        val uid = user?.uid
        val usersRef: DatabaseReference = database.getReference("users")

        if (uid != null) {
            usersRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userData = snapshot.getValue(UsersObj::class.java)
                        userData?.let { onDataRead(it) }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onError(error)
                }
            })
        }
    }

    fun getUserList(onUsersListReady: (List<UsersObj>) -> Unit, onError: (DatabaseError) -> Unit){
        val usersList = mutableListOf<UsersObj>()
        val usersRef: DatabaseReference = database.getReference("users")
        usersRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                for (userSnapshot in snapshot.children){
                    val user = userSnapshot.getValue(UsersObj::class.java)
                    user?.let {
                        Log.d("FirebaseDatabaseManager", "User uid: ${it.uid}")
                        usersList.add(it)}
                }
                onUsersListReady(usersList)
            }
            override fun onCancelled(error: DatabaseError){
                onError(error)
            }
        })

        FirebaseDatabaseManager().readUserData(
            onDataRead = { user ->
                usersList.add(user)
                onUsersListReady(usersList)
            },
            onError = {error ->
                onError(error)
            }
        )

    }



}