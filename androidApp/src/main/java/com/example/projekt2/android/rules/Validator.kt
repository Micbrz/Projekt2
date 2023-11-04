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
    fun validateRepeatPassword(password:String,repeatpassword:String) :ValidationResult{
        val isMatching = repeatpassword == password
        println("Password: $password")
        println("Repeat Password: $repeatpassword")
        println("isMatching: $isMatching")


        return ValidationResult(
            (!repeatpassword.isNullOrEmpty() && repeatpassword.length >= 5 && (repeatpassword == password))
        )
    }
}
data class ValidationResult(
    val status :Boolean = false
)