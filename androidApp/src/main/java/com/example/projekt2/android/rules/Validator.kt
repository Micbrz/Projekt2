package com.example.projekt2.android.rules

object Validator {

    fun validateEmail(email:String) :ValidationResult{
        return ValidationResult(
            (!email.isNullOrEmpty() && email.length >= 10)
        )
    }
    fun validatePassword(password:String) :ValidationResult{
        return ValidationResult(
            (!password.isNullOrEmpty() && password.length >= 5 )
        )
    }
    fun validateName(name:String) :ValidationResult{
        return ValidationResult(
            (!name.isNullOrEmpty())
        )
    }
    fun validateLastName(lastname:String) :ValidationResult{
        return ValidationResult(
            (!lastname.isNullOrEmpty())
        )
    }
}
data class ValidationResult(
    val status :Boolean = false
)