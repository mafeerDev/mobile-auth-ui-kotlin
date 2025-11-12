package com.example.examen_app.ui.screen.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val emailTouched: Boolean = false,
    val passwordTouched: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

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
            return "La contraseña no debe exceder los 8 caracteres"
        }
        return null
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
                passwordError = validatePassword(newPass)
            )
        }
    }

    fun onLoginClick(onLoginSuccess: () -> Unit) {
        val emailError = validateEmail(_uiState.value.email)
        val passwordError = validatePassword(_uiState.value.password)

        _uiState.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError,
                emailTouched = true,
                passwordTouched = true
            )
        }

        if (emailError == null && passwordError == null) {
            _uiState.update { it.copy(isLoading = true) }
            println("Login exitoso: ${uiState.value.email}")
            onLoginSuccess()
        }
    }
}