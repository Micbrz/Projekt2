package com.example.projekt2.android.Data
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens
import com.example.projekt2.android.rules.Validator
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel : ViewModel(){
    private val TAG = SignUpViewModel::class.simpleName
    var SignUpUIState = mutableStateOf(SignUpUIState())
    var allValidationsPassed = mutableStateOf(false)
    fun onEvent(event:SignUpUIEvent){
        validateDataWidthRules()
        when(event){
            is SignUpUIEvent.EmailChanged -> {
                SignUpUIState.value = SignUpUIState.value.copy(
                    email = event.email
                )

                printState()
            }
            is SignUpUIEvent.PasswordChanged -> {
                SignUpUIState.value = SignUpUIState.value.copy(
                    password = event.password
                )

                printState()
            }
            is SignUpUIEvent.RepeatPasswordChanged -> {
                SignUpUIState.value = SignUpUIState.value.copy(
                    repeatpassword = event.repeatpassword
                )

                printState()
            }
            is SignUpUIEvent.RegisterButtonClicked -> {
                signUp()
            }


        }
    }

    private fun signUp() {
        Log.d(TAG, "Inside_signUp")
        printState()

        validateDataWidthRules()

        createUserInFirebase(
            email = SignUpUIState.value.email,
            password = SignUpUIState.value.password

        )
    }

    private fun validateDataWidthRules() {
        val emailResult = Validator.validateEmail(
            email = SignUpUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = SignUpUIState.value.password
        )
        val repeatpasswordResult = Validator.validateRepeatPassword(
            repeatpassword = SignUpUIState.value.repeatpassword
        )
        Log.d(TAG,"Inside_validateDataWithRules")
        Log.d(TAG,"emailResult= $emailResult")
        Log.d(TAG,"passwordResult= $passwordResult")
        Log.d(TAG,"repeatpasswordResult= $repeatpasswordResult")
        SignUpUIState.value = SignUpUIState.value.copy(
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
        Log.d(TAG, SignUpUIState.value.toString())
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
    fun logout() {

        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG,"Inside signout success")
                PostOfficeAppRouter.navigateTo(Screens.SignIn)
            }
            else{
                Log.d(TAG,"Inside sign out is not complete")
                PostOfficeAppRouter.navigateTo(Screens.SignIn)
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
}