package com.example.projekt2.android.Data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens
import com.example.projekt2.android.rules.Validator
import com.google.firebase.auth.FirebaseAuth


class LoginViewModel : ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var loginUIState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false)
    fun onEvent(event:LoginUIEvent){
    when(event){
        is LoginUIEvent.EmailChanged ->{
            loginUIState.value = loginUIState.value.copy(
                email = event.email
            )
        }
        is LoginUIEvent.PasswordChanged ->{
            loginUIState.value = loginUIState.value.copy(
                password = event.password
            )
        }
        is LoginUIEvent.LoginButtonClicked ->{
            login()
        }
    }
        validateDataWidthRules()
}


    private fun validateDataWidthRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )
        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        allValidationsPassed.value = emailResult.status && passwordResult.status
}

    private fun login() {
        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                Log.d(TAG,"Inside_login_Success")
                Log.d(TAG,"${it.isSuccessful}")
                if(it.isSuccessful){
                    loginInProgress.value = false
                    PostOfficeAppRouter.navigateTo(Screens.HomeScreen)

                }
            }
            .addOnFailureListener{
                Log.d(TAG,"Inside_login_failure")
                Log.d(TAG,"${it.localizedMessage}")
                loginInProgress.value = false
            }
    }
}