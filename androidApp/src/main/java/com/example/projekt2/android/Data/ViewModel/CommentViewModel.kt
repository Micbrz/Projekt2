package com.example.projekt2.android.Data.ViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projekt2.android.Data.Models.CommentModel
import com.example.projekt2.android.Data.Models.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CommentViewModel: ViewModel(){
    private val db = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")


    fun addComment(threadId: String, comment: String, userId: String) {
        val commentData = CommentModel(comment,userId, System.currentTimeMillis().toString())

        val threadRef = db.getReference("comments")
        val commentRef = threadRef.child("$threadId").push()
        commentRef.setValue(commentData)
            .addOnSuccessListener {
                // Sukces - komentarz został dodany
            }
            .addOnFailureListener { e ->
                // Błąd podczas dodawania komentarza
                Log.e(TAG, "Nie udało się dodać komentarza: ${e.message}")
            }
}
    fun getCommentsForThread(threadId: String, callback: (List<CommentModel>) -> Unit) {
        val threadRef = db.getReference("comments").child(threadId)
        val commentsList = mutableListOf<CommentModel>()

        threadRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val comment = childSnapshot.getValue(CommentModel::class.java)
                    comment?.let { commentsList.add(it) }
                }
                callback(commentsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Błąd podczas pobierania komentarzy: ${error.message}")
            }
        })
    }
    fun getUserByComment(comment: CommentModel, callback: (UserModel?) -> Unit) {
        val userCommentID = comment.userId
        val userRef = db.getReference("users").child(userCommentID)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                callback(user)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Błąd podczas pobierania użytkownika: ${error.message}")
                callback(null)
            }
        })
    }

}