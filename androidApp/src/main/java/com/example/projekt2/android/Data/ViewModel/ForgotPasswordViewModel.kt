package com.example.projekt2.android.Data.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    fun resetPassword(email:String): LiveData<Boolean>{
        val result = MutableLiveData<Boolean>()
        if(email.isNotEmpty()){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    result.value = true
                }else{
                    result.value = false
                }
            }
            }else{
                result.value = false
            }
        return result
    }
}