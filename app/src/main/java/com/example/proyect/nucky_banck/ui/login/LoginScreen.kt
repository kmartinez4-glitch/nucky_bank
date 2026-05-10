package com.example.proyect.nucky_banck.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyect.nucky_banck.R
import com.example.proyect.nucky_banck.ui.components.NuckyTextField
import com.example.proyect.nucky_banck.ui.components.ShowLoadingAlertDialog
import com.example.proyect.nucky_banck.ui.components.ShowMessageAlertDialog
import com.example.proyect.nucky_banck.ui.navigation.NavRoutes
import com.example.proyect.nucky_banck.ui.theme.NuckyColors



@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    navController: NavController,
    onLoginSuccess: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showMessageAlert by remember { mutableStateOf(false) }
    var titleDialog by remember { mutableStateOf("Error") }
    var messageDialog by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val focusManager = LocalFocusManager.current

    if (uiState.isLoading) {
        ShowLoadingAlertDialog()
    }

    if (showMessageAlert) {
        ShowMessageAlertDialog(
            onConfirmation = { showMessageAlert = false },
            dialogTitle = titleDialog,
            dialogText = messageDialog
        )
    }

    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            onLoginSuccess(uiState.cedula)
        }
    }

    LaunchedEffect(uiState.generalError) {
        uiState.generalError?.let {
            titleDialog = "Error de autenticación"
            messageDialog = it
            showMessageAlert = true
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
                Brush.verticalGradient(
                    listOf(
                        NuckyColors.NavyBlue,
                        NuckyColors.DeepBlue
                    )
                )
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),

            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // HEADER
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.text_welcome),
                    fontSize = 42.sp,
                    color = NuckyColors.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Nucky Bank",
                    color = NuckyColors.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tu dinero, seguro y disponible",
                    color = NuckyColors.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }

            // CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = NuckyColors.White)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(28.dp)) {
                    Text(
                        text = "Iniciar sesión",
                        color = NuckyColors.TextDark,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    NuckyTextField(
                        value = uiState.cedula,
                        onValueChange = viewModel::onCedulaChange,
                        label = "Número de cédula",
                        keyboardType = KeyboardType.Number,
                        leadingIcon = { Icon(Icons.Default.Person, null, tint = NuckyColors.NavyBlue) },
                        errorMessage = uiState.cedulaError
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    NuckyTextField(
                        value = uiState.password,
                        onValueChange = viewModel::onPasswordChange,
                        label = "Contraseña",
                        isPassword = true,
                        leadingIcon = { Icon(Icons.Default.Lock, null, tint = NuckyColors.NavyBlue) },
                        errorMessage = uiState.passwordError
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.onLoginClicked() },
                        enabled = !uiState.isLoading,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NuckyColors.Emerald)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(color = NuckyColors.White, modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                        } else {
                            Text(text = "Iniciar sesión", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = "¿No tienes cuenta?", color = NuckyColors.TextGray)
                        TextButton(onClick = { navController.navigate(NavRoutes.REGISTER) }) {
                            Text(text = "Regístrate", color = NuckyColors.NavyBlue, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
