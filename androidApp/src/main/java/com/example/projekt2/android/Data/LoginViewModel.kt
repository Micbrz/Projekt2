package com.example.projekt2.android.Data
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.projekt2.android.rules.Validator
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel(){
    private val TAG = LoginViewModel::class.simpleName
    var registrationUIState = mutableStateOf(RegistrationUIState())
    var allValidationsPassed = mutableStateOf(false)
    fun onEvent(event:UIEvent){
        validateDataWidthRules()
        when(event){
            is UIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )

                printState()
            }
            is UIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )

                printState()
            }
            is UIEvent.RepeatPasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    repeatpassword = event.repeatpassword
                )

                printState()
            }
            is UIEvent.RegisterButtonClicked -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        Log.d(TAG, "Inside_signUp")
        printState()

        validateDataWidthRules()

        createUserInFirebase(
            email = registrationUIState.value.email,
            password = registrationUIState.value.password

        )
    }

    private fun validateDataWidthRules() {
        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )
        val repeatpasswordResult = Validator.validateRepeatPassword(
            repeatpassword = registrationUIState.value.repeatpassword
        )
        Log.d(TAG,"Inside_validateDataWithRules")
        Log.d(TAG,"emailResult= $emailResult")
        Log.d(TAG,"passwordResult= $passwordResult")
        Log.d(TAG,"repeatpasswordResult= $repeatpasswordResult")
        registrationUIState.value = registrationUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            repeatpasswordError = repeatpasswordResult.status
        )
        if(emailResult.status && passwordResult.status && repeatpasswordResult.status){
            allValidationsPassed.value = true
        }else{
            allValidationsPassed.value = false
        }
    }

    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, registrationUIState.value.toString())
    }
    private fun createUserInFirebase(email:String, password:String){
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                Log.d(TAG,"Inside_OnCompleteListener")
                Log.d(TAG,"isSuccessful = ${it.isSuccessful}")
            }
            .addOnFailureListener{
                Log.d(TAG,"Inside_OnFailureListener")
                Log.d(TAG,"Exception = ${it.message}")
                Log.d(TAG,"Exception = ${it.localizedMessage}")
            }
    }
}