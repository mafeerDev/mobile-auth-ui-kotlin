package com.example.examen_app.ui.screen.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examen_app.R
import com.example.examen_app.ui.components.Input
import com.example.examen_app.ui.components.PrimaryButton
import com.example.examen_app.ui.components.SocialLoginButton
import com.example.examen_app.ui.theme.Examen_appTheme
import com.example.examen_app.ui.theme.LightGray

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onRegisterSuccess: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    RegisterScreenContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onRegisterClick = { viewModel.onRegisterClick(onRegisterSuccess) },
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Regresar") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Create your account",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Signup",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(32.dp))

            Input(
                value = uiState.name,
                onValueChange = onNameChange,
                label = "Name",
                isError = uiState.nameTouched && uiState.nameError != null,
                errorMessage = if (uiState.nameTouched) uiState.nameError else null
            )

            Spacer(Modifier.height(16.dp))

            Input(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = "Email",
                isError = uiState.emailTouched && uiState.emailError != null,
                errorMessage = if (uiState.emailTouched) uiState.emailError else null,
                keyboardType = KeyboardType.Email
            )

            Spacer(Modifier.height(16.dp))

            Input(
                value = uiState.password,
                onValueChange = onPasswordChange,
                label = "Password",
                isError = uiState.passwordTouched && uiState.passwordError != null,
                errorMessage = if (uiState.passwordTouched) uiState.passwordError else null,
                keyboardType = KeyboardType.Password,
                isPasswordToggle = true
            )

            Spacer(Modifier.height(16.dp))

            Input(
                value = uiState.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = "Confirm Password",
                isError = uiState.confirmPasswordTouched && uiState.confirmPasswordError != null,
                errorMessage = if (uiState.confirmPasswordTouched) uiState.confirmPasswordError else null,
                keyboardType = KeyboardType.Password,
                isPasswordToggle = true
            )

            Spacer(Modifier.height(32.dp))

            PrimaryButton(
                text = "Signup",
                enabled = !uiState.isLoading,
                onClick = onRegisterClick
            )

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(1f), color = LightGray)
                Text("Or", modifier = Modifier.padding(horizontal = 8.dp), color = Color.Gray)
                Divider(modifier = Modifier.weight(1f), color = LightGray)
            }

            Spacer(Modifier.height(32.dp))

            SocialLoginButton(
                text = "Login with Facebook",
                icon = painterResource(id = R.drawable.logosfacebook),
                onClick = { /* TODO */ },
                containerColor = Color(0xFFF1F5F9),
                contentColor = Color.Black,
                borderColor = Color(0xFFE2E8F0)
            )

            Spacer(Modifier.height(16.dp))

            SocialLoginButton(
                text = "Login with Google",
                icon = painterResource(id = R.drawable.materialiconthemegoogle),
                onClick = { /* TODO */ }
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, name = "Register - Estado por Defecto")
@Composable
fun RegisterScreenPreview_Default() {
    Examen_appTheme {
        RegisterScreenContent(
            uiState = RegisterUiState(),
            onNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onRegisterClick = {},
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Register - Errores Complejos")
@Composable
fun RegisterScreenPreview_ComplexErrors() {
    Examen_appTheme {
        RegisterScreenContent(
            uiState = RegisterUiState(
                name = "Angel123",
                nameTouched = true,
                nameError = "Solo letras",
                email = "correo-invalido",
                emailTouched = true,
                emailError = "Email inválido",
                password = "pass",
                passwordTouched = true,
                passwordError = "Mín. 8 char",
                confirmPassword = "123",
                confirmPasswordTouched = true,
                confirmPasswordError = "No coinciden"
            ),
            onNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onRegisterClick = {},
            onNavigateBack = {}
        )
    }
}