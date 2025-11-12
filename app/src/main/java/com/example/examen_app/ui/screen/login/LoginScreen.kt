package com.example.examen_app.ui.screen.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
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
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = { viewModel.onLoginClick(onLoginSuccess) },
        onNavigateToRegister = onNavigateToRegister,
        onForgotClick = { /* TODO */ }
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onForgotClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(32.dp))
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Welcome back!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(32.dp))

        Input(
            value = uiState.email,
            onValueChange = onEmailChange,
            label = "Email",
            shape = RoundedCornerShape(12.dp),
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

        TextButton(
            onClick = onForgotClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Forgot Password?",
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(24.dp))

        PrimaryButton(
            text = "Login",
            enabled = !uiState.isLoading,
            onClick = onLoginClick
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Don't have an account? ", color = Color.Black)
            Text(
                text = "Signup",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateToRegister() }
            )
        }

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

@Preview(showBackground = true, name = "Login - Estado por Defecto")
@Composable
fun LoginScreenPreview_Default() {
    Examen_appTheme {
        LoginScreenContent(
            uiState = LoginUiState(),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onNavigateToRegister = {},
            onForgotClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Login - Estado con Errores")
@Composable
fun LoginScreenPreview_WithErrors() {
    Examen_appTheme {
        LoginScreenContent(
            uiState = LoginUiState(
                email = "correo-invalido",
                emailTouched = true,
                emailError = "El formato del email no es válido",
                password = "masde8char",
                passwordTouched = true,
                passwordError = "La contraseña no debe exceder los 8 caracteres"
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onNavigateToRegister = {},
            onForgotClick = {}
        )
    }
}