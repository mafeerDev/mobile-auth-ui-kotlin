package com.example.examen_app.ui.screen.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val nameTouched: Boolean = false,
    val emailTouched: Boolean = false,
    val passwordTouched: Boolean = false,
    val confirmPasswordTouched: Boolean = false
)

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private fun validateName(name: String): String? {
        if (name.isBlank()) {
            return "El nombre es requerido"
        }
        val nameRegex = Regex("^[a-zA-Z\\s]*$")
        if (!name.matches(nameRegex)) {
            return "El nombre solo debe contener letras y espacios"
        }
        return null
    }

    private fun validateEmail(email: String): String? {
        if (email.isBlank()) {
            return "El email es requerido"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "El formato del email no es válido"
        }
        return null
    }

    private fun validatePassword(password: String): String? {
        if (password.isBlank()) {
            return "La contraseña es requerida"
        }
        if (password.length > 8) {
            return "Máximo 8 caracteres"
        }
        if (!password.any { it.isDigit() }) {
            return "Debe tener al menos un número"
        }
        if (!password.any { it.isUpperCase() }) {
            return "Debe tener al menos una mayúscula"
        }
        if (!password.any { !it.isLetterOrDigit() }) {
            return "Debe tener un caracter especial (ej. @, $, !, %)"
        }
        return null
    }

    private fun validateConfirmPassword(password: String, confirm: String): String? {
        if (confirm.isBlank()) {
            return "La confirmación es requerida"
        }
        if (password != confirm) {
            return "Las contraseñas no coinciden"
        }
        return null
    }


    fun onNameChange(newName: String) {
        _uiState.update {
            it.copy(
                name = newName,
                nameTouched = true,
                nameError = validateName(newName)
            )
        }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update {
            it.copy(
                email = newEmail,
                emailTouched = true,
                emailError = validateEmail(newEmail)
            )
        }
    }

    fun onPasswordChange(newPass: String) {
        _uiState.update {
            it.copy(
                password = newPass,
                passwordTouched = true,
                passwordError = validatePassword(newPass),
                confirmPasswordError = if(it.confirmPasswordTouched) {
                    validateConfirmPassword(newPass, it.confirmPassword)
                } else {
                    null
                }
            )
        }
    }

    fun onConfirmPasswordChange(newCPass: String) {
        _uiState.update {
            it.copy(
                confirmPassword = newCPass,
                confirmPasswordTouched = true,
                confirmPasswordError = validateConfirmPassword(it.password, newCPass)
            )
        }
    }

    fun onRegisterClick(onRegisterSuccess: () -> Unit) {
        val nameError = validateName(_uiState.value.name)
        val emailError = validateEmail(_uiState.value.email)
        val passError = validatePassword(_uiState.value.password)
        val cPassError = validateConfirmPassword(_uiState.value.password, _uiState.value.confirmPassword)

        _uiState.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passError,
                confirmPasswordError = cPassError,
                nameTouched = true,
                emailTouched = true,
                passwordTouched = true,
                confirmPasswordTouched = true
            )
        }

        if (nameError == null && emailError == null && passError == null && cPassError == null) {
            _uiState.update { it.copy(isLoading = true) }
            println("Registro exitoso: ${uiState.value.name}")
            onRegisterSuccess()
        }
    }
}