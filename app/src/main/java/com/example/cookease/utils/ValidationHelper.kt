package com.example.cookease.utils

object ValidationHelper {
    fun validateRecipe(
        name: String,
        instructions: String,
        ingredients: List<String>
    ): ValidationResult {
        return when {
            name.isBlank() -> ValidationResult.Error("Recipe name is required")
            name.length < 3 -> ValidationResult.Error("Recipe name must be at least 3 characters")
            instructions.isBlank() -> ValidationResult.Error("Instructions are required")
            instructions.length < 20 -> ValidationResult.Error("Instructions must be at least 20 characters")
            ingredients.isEmpty() || ingredients.all { it.isBlank() } ->
                ValidationResult.Error("At least one ingredient is required")
            else -> ValidationResult.Success
        }
    }

    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Error("Email is required")
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                ValidationResult.Error("Invalid email format")
            else -> ValidationResult.Success
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult.Error("Password is required")
            password.length < 6 -> ValidationResult.Error("Password must be at least 6 characters")
            else -> ValidationResult.Success
        }
    }

    fun validateDisplayName(name: String): ValidationResult {
        return when {
            name.isBlank() -> ValidationResult.Error("Display name is required")
            name.length < 2 -> ValidationResult.Error("Display name must be at least 2 characters")
            else -> ValidationResult.Success
        }
    }
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}
