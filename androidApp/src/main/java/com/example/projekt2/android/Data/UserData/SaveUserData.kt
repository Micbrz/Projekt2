package com.example.projekt2.android.Data.UserData

import android.util.Log
import com.example.projekt2.android.Data.Models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FirebaseDatabaseManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")

    fun saveUserData(employeeObj: UserModel){
        val user = auth.currentUser
        val uid = user?.uid
        val usersRef: DatabaseReference = database.getReference("users")
        val imageRef = usersRef.child("users")
        val firestoreDb= Firebase.firestore
        val followersRef = firestoreDb.collection("followers").document(uid!!)
        val followingRef = firestoreDb.collection("following").document(uid!!)

        followingRef.set(mapOf("followingIds" to listOf<String>()))
        followersRef.set(mapOf("followersIds" to listOf<String>()))
        if(uid != null){
            val userData = mapOf(
                "Bio" to employeeObj.bio,
                "Name" to employeeObj.name,
                "LastName" to employeeObj.lastname,
                "Picture" to employeeObj.image,
                "uid" to employeeObj.uid
            )
            val userKey = usersRef.push().key // generowanie unikalnego klucza użytkownika
            if (userKey != null){
                usersRef.child(uid).setValue(userData)
            }

        }
    }
    fun readUserData(onDataRead: (UserModel) -> Unit, onError: (DatabaseError) -> Unit) {
        val user = auth.currentUser
        val uid = user?.uid
        val usersRef: DatabaseReference = database.getReference("users")



        if (uid != null) {
            usersRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userData = snapshot.getValue(UserModel::class.java)
                        userData?.let { onDataRead(it) }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onError(error)
                }
            })
        }
    }

    fun getUserList(onUsersListReady: (List<UserModel>) -> Unit, onError: (DatabaseError) -> Unit){
        val usersList = mutableListOf<UserModel>()
        val usersRef: DatabaseReference = database.getReference("users")
        usersRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                for (userSnapshot in snapshot.children){
                    val user = userSnapshot.getValue(UserModel::class.java)
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