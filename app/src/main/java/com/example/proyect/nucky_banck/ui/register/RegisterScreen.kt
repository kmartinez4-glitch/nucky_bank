package com.example.proyect.nucky_banck.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.proyect.nucky_banck.ui.components.ShowLoadingAlertDialog
import com.example.proyect.nucky_banck.ui.components.ShowMessageAlertDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalFocusManager
import com.example.proyect.nucky_banck.ui.components.NuckyTextField
import com.example.proyect.nucky_banck.ui.theme.NuckyColors

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    navController: NavController
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val focusManager = LocalFocusManager.current

    var showMessageDialog by remember { mutableStateOf(false) }

    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    // Loading
    if (uiState.isLoading) {
        ShowLoadingAlertDialog()
    }

    // Dialog mensajes
    if (showMessageDialog) {

        ShowMessageAlertDialog(
            onConfirmation = {

                showMessageDialog = false

                // Si registro exitoso vuelve al login
                if (uiState.registerSuccess) {
                    navController.popBackStack()
                }
            },

            dialogTitle = dialogTitle,
            dialogText = dialogMessage
        )
    }

    // Registro exitoso
    LaunchedEffect(uiState.registerSuccess) {

        if (uiState.registerSuccess) {

            dialogTitle = "Registro exitoso"
            dialogMessage = "Tu cuenta fue creada correctamente"

            showMessageDialog = true
        }
    }

    // Error general
    LaunchedEffect(uiState.generalError) {

        uiState.generalError?.let {

            dialogTitle = "Error"
            dialogMessage = it

            showMessageDialog = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ) {
                focusManager.clearFocus()
            }
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),

            verticalArrangement = Arrangement.SpaceBetween
        ){

            // HEADER
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Crear cuenta",
                    color = NuckyColors.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Únete a Nucky Bank",
                    color = NuckyColors.White.copy(alpha = 0.8f),
                    fontSize = 15.sp
                )
            }

            // CARD
            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp
                ),

                colors = CardDefaults.cardColors(
                    containerColor = NuckyColors.White
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 28.dp,
                            vertical = 36.dp
                        )
                ) {

                    Text(
                        text = "Registro",
                        color = NuckyColors.TextDark,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Nombre
                    NuckyTextField(
                        value = uiState.fullName,
                        onValueChange = viewModel::onFullNameChange,
                        label = "Nombre completo",

                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = NuckyColors.NavyBlue
                            )
                        },

                        errorMessage = uiState.fullNameError
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Cédula
                    NuckyTextField(
                        value = uiState.cedula,
                        onValueChange = viewModel::onCedulaChange,
                        label = "Número de cédula",

                        keyboardType = KeyboardType.Number,

                        leadingIcon = {
                            Icon(
                                Icons.Default.Badge,
                                contentDescription = null,
                                tint = NuckyColors.NavyBlue
                            )
                        },

                        errorMessage = uiState.cedulaError
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password
                    NuckyTextField(
                        value = uiState.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = "Contraseña",

                        isPassword = true,

                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = NuckyColors.NavyBlue
                            )
                        },

                        errorMessage = uiState.passwordError
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm password
                    NuckyTextField(
                        value = uiState.confirmPassword,
                        onValueChange = viewModel::onConfirmPasswordChange,
                        label = "Confirmar contraseña",

                        isPassword = true,

                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = NuckyColors.NavyBlue
                            )
                        },

                        errorMessage = uiState.confirmPasswordError
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // BOTÓN
                    Button(
                        onClick = {
                            viewModel.onRegisterClicked()
                        },

                        enabled = !uiState.isLoading,

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),

                        shape = RoundedCornerShape(12.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = NuckyColors.Emerald,
                            disabledContainerColor =
                                NuckyColors.Emerald.copy(alpha = 0.5f)
                        )
                    ) {

                        if (uiState.isLoading) {

                            CircularProgressIndicator(
                                color = NuckyColors.White,
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.dp
                            )

                        } else {

                            Text(
                                text = "Registrarse",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Login
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "¿Ya tienes cuenta?",
                            color = NuckyColors.TextGray
                        )

                        TextButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {

                            Text(
                                text = "Inicia sesión",
                                color = NuckyColors.NavyBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {

    val navController = rememberNavController()

    RegisterScreen(
        navController = navController
    )
}