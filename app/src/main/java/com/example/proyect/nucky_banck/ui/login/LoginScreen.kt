package com.example.proyect.nucky_banck.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

object NuckyColors {
    val NavyBlue = Color(0xFF1A237E)
    val DeepBlue = Color(0xFF283593)
    val Emerald = Color(0xFF00897B)
    val LightGray = Color(0xFFF5F5F5)
    val TextDark = Color(0xFF212121)
    val TextGray = Color(0xFF757575)
    val ErrorRed = Color(0xFFD32F2F)
    val White = Color(0xFFFFFFFF)
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (String) -> Unit = {}  // ✅ AGREGADO — recibe la cédula
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // ✅ MODIFICADO — ahora llama onLoginSuccess con la cédula
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            onLoginSuccess(uiState.cedula)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        NuckyColors.NavyBlue,
                        NuckyColors.DeepBlue
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderSection()
            LoginCard(
                uiState = uiState,
                onCedulaChange = viewModel::onCedulaChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLoginClick = viewModel::onLoginClicked
            )
        }
    }
}

@Composable
private fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🏦", fontSize = 56.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Nucky Bank",
            color = NuckyColors.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tu dinero, seguro y siempre disponible",
            color = NuckyColors.White.copy(alpha = 0.75f),
            fontSize = 14.sp
        )
    }
}

@Composable
private fun LoginCard(
    uiState: LoginModel,
    onCedulaChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        colors = CardDefaults.cardColors(containerColor = NuckyColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp, vertical = 36.dp)
        ) {
            Text(
                text = "Bienvenido de nuevo",
                color = NuckyColors.TextDark,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Ingresa tus datos para continuar",
                color = NuckyColors.TextGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 28.dp)
            )
            NuckyTextField(
                value = uiState.cedula,
                onValueChange = onCedulaChange,
                label = "Número de cédula",
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null, tint = NuckyColors.NavyBlue)
                },
                errorMessage = uiState.cedulaError,
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(16.dp))
            NuckyTextField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                label = "Contraseña",
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = NuckyColors.NavyBlue)
                },
                errorMessage = uiState.passwordError,
                isPassword = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (uiState.generalError != null) {
                Text(
                    text = uiState.generalError,
                    color = NuckyColors.ErrorRed,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onLoginClick,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NuckyColors.Emerald,
                    disabledContainerColor = NuckyColors.Emerald.copy(alpha = 0.5f)
                )
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        color = NuckyColors.White,
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "Iniciar sesión", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = { },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = NuckyColors.NavyBlue,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun NuckyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = leadingIcon,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text(text = errorMessage, color = NuckyColors.ErrorRed, fontSize = 12.sp)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = NuckyColors.NavyBlue,
            unfocusedBorderColor = Color(0xFFBDBDBD),
            errorBorderColor = NuckyColors.ErrorRed,
            focusedLabelColor = NuckyColors.NavyBlue
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}