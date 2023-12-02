package com.example.projekt2.android.Data

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SearchViewModel : ViewModel() {
    private val database = Firebase.database
    private val usersRef = database.getReference("users")

    fun searchUsers(query: String, onSearchResult: (List<UsersObj>) -> Unit) {
        val userList = mutableListOf<UsersObj>()

        // Przeszukanie bazy danych w celu znalezienia użytkowników na podstawie zapytania (np. imienia)
        val queryRef = usersRef.orderByChild("Name").startAt(query).endAt(query + "\uf8ff")

        queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UsersObj::class.java)
                    user?.let {
                        userList.add(it)
                    }
                }
                onSearchResult(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Obsługa błędu pobierania danych
            }
        })
    }
}
