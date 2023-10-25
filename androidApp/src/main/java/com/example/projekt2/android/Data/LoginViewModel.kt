package com.example.projekt2.android.Data
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
class LoginViewModel : ViewModel(){

    var registrationUIState = mutableStateOf(RegistrationUIState())
    fun onEvent(event:UIEvent){
        when(event){
            is UIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }
            is UIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }
            is UIEvent.RepeatPasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    repeatpassword = event.repeatpassword
                )
            }
        }
    }
}