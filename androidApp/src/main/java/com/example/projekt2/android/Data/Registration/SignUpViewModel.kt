package com.example.projekt2.android.Data.Registration
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.projekt2.android.Data.UsersObj
import com.example.projekt2.android.navigation.PostOfficeAppRouter
import com.example.projekt2.android.navigation.Screens
import com.example.projekt2.android.rules.Validator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpViewModel : ViewModel(){
    private val TAG = SignUpViewModel::class.simpleName
    var SignUpUIState = mutableStateOf(SignUpUIState())
    var allValidationsPassed = mutableStateOf(false)
    fun onEvent(event: SignUpUIEvent){
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
            is SignUpUIEvent.NameChanged -> {
                SignUpUIState.value = SignUpUIState.value.copy(
                    name = event.name
                )

                printState()
            }
            is SignUpUIEvent.LastNameChanged -> {
                SignUpUIState.value = SignUpUIState.value.copy(
                    lastname = event.lastname
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
            password = SignUpUIState.value.password,
            name = SignUpUIState.value.name,
            lastname = SignUpUIState.value.lastname
        )
    }

    private fun validateDataWidthRules() {
        val emailResult = Validator.validateEmail(
            email = SignUpUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = SignUpUIState.value.password
        )
        val nameResult = Validator.validateName(
            name = SignUpUIState.value.name
        )
        val lastnameResult = Validator.validateLastName(
            lastname = SignUpUIState.value.lastname
        )
        Log.d(TAG,"Inside_validateDataWithRules")
        Log.d(TAG,"emailResult= $emailResult")
        Log.d(TAG,"passwordResult= $passwordResult")
        Log.d(TAG,"nameResult= $nameResult")
        Log.d(TAG,"lastnameResult= $lastnameResult")
        SignUpUIState.value = SignUpUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            nameError = nameResult.status,
            lastnameError = lastnameResult.status
        )
        if(emailResult.status && passwordResult.status &&  nameResult.status && lastnameResult.status){
            allValidationsPassed.value = true
        }else{
            allValidationsPassed.value = false
        }


    }


    private fun printState(){
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, SignUpUIState.value.toString())
    }
    private fun createUserInFirebase(email:String, password:String, name:String, lastname:String){
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                Log.d(TAG,"Inside_OnCompleteListener")
                Log.d(TAG,"isSuccessful = ${it.isSuccessful}")
                val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://clonefbandroidios-default-rtdb.europe-west1.firebasedatabase.app")
                val user = FirebaseAuth.getInstance().currentUser
                val uid = user?.uid

                // Utwórz obiekt UsersObj z podstawowymi informacjami
                val newUser = UsersObj(
                    uid = uid ?: "",
                    Name = name,
                    LastName = lastname,
                    Bio = "", // Tutaj możesz dodać domyślne wartości dla Bio i Picture
                    Picture = ""
                )
                fun saveUserData(userObj: UsersObj) {
                    val usersRef: DatabaseReference = database.getReference("users")

                    // Zapisz informacje o użytkowniku do bazy danych Firebase
                    usersRef.child(userObj.uid).setValue(userObj)
                }

                // Zapisz podstawowe informacje użytkownika do bazy danych Firebase
                saveUserData(newUser)



                PostOfficeAppRouter.navigateTo(Screens.SignIn)


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