package com.example.projekt2.android.Data.Login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekt2.android.rules.Validator
import com.google.firebase.auth.FirebaseAuth
sealed class LoginResult {
    object SUCCESS : LoginResult()
    object FAILURE : LoginResult()
    // Możesz dodać dodatkowe stany logowania, jeśli są potrzebne
}

class LoginViewModel  : ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var loginUIState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)
    var loginInProgress = mutableStateOf(false)
    val loginResult: LiveData<LoginResult> get() = _loginResult
    private val _loginResult = MutableLiveData<LoginResult>()

    fun onEvent(event: LoginUIEvent){
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
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                Log.d(TAG,"Inside_login_Success")
                Log.d(TAG,"${it.isSuccessful}")
                if(it.isSuccessful) {
                    _loginResult.postValue(LoginResult.SUCCESS)
                    Log.d(TAG, "User UID: $uid")

                }


            }
            .addOnFailureListener{exception ->
                Log.e(TAG, "Błąd logowania: ${exception.localizedMessage}")
                _loginResult.postValue(LoginResult.FAILURE)
                loginInProgress.value = false
            }
    }
}
