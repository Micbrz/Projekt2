package com.example.projekt2.android.rules

import java.util.regex.Pattern

object Validator {
    val pattern = Pattern.compile("^[a-zA-Z0-9]+$")

    fun validateEmail(email:String) :ValidationResult{
        val pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{1,5})$")
        val matcher = pattern.matcher(email)
        return ValidationResult(
            (!email.isNullOrEmpty() && email.length >= 10 && matcher.matches())
        )
    }
    fun validatePassword(password:String) :ValidationResult{
        return ValidationResult(
            (!password.isNullOrEmpty() && password.length >= 5 )
        )
    }
    fun validateName(name:String) :ValidationResult{
        val matcher = pattern.matcher(name)
        return ValidationResult(
            (!name.isNullOrEmpty() && name.length >=3 && matcher.matches())
        )
    }
    fun validateLastName(lastname:String) :ValidationResult{
        val matcher = pattern.matcher(lastname)
        return ValidationResult(
            (!lastname.isNullOrEmpty() && lastname.length >=3 && matcher.matches())
        )
    }
}
data class ValidationResult(
    val status :Boolean = false
)