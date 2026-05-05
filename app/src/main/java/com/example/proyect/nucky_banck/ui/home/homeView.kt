package com.example.proyect.nucky_banck.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

private val NavyBlue = Color(0xFF1A237E)
private val DeepBlue = Color(0xFF283593)
private val Emerald  = Color(0xFF00897B)
private val White    = Color(0xFFFFFFFF)
private val TextDark = Color(0xFF212121)
private val TextGray = Color(0xFF757575)
private val LineGray = Color(0xFFEEEEEE)

@Composable
fun HomeScreen(
    cedula: String,
    onLogout: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Cuando la pantalla abre, busca el nombre en Firebase
    LaunchedEffect(cedula) {
        viewModel.loadUserData(cedula)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(NavyBlue, DeepBlue)))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TopBar(
                nombre = uiState.nombreUsuario,
                onLogout = onLogout
            )
            HomeCard(saldo = uiState.saldo)
        }
    }
}

@Composable
private fun TopBar(nombre: String, onLogout: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 56.dp, start = 24.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Bienvenido de nuevo",
                color = White.copy(alpha = 0.75f),
                fontSize = 14.sp
            )
            Text(
                text = nombre,   // muestra el nombre referente al numero de Cedula
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        IconButton(onClick = onLogout) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Cerrar sesión",
                tint = White
            )
        }
    }
}

@Composable
private fun HomeCard(saldo: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp, vertical = 36.dp)
        ) {
            Text(text = "Saldo disponible", color = TextGray, fontSize = 14.sp)
            Text(
                text = "$ %,.2f".format(saldo),
                color = NavyBlue,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Emerald.copy(alpha = 0.12f)
            ) {
                Text(
                    text = "  Cuenta de ahorros  ",
                    color = Emerald,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            HorizontalDivider(color = LineGray)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Acciones rápidas", color = TextDark, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ActionButton(emoji = "💸", label = "Transferir", modifier = Modifier.weight(1f))
                ActionButton(emoji = "📄", label = "Extracto",   modifier = Modifier.weight(1f))
                ActionButton(emoji = "💳", label = "Recargar",   modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Últimos movimientos", color = TextDark, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Sin movimientos recientes", color = TextGray, fontSize = 14.sp)
        }
    }
}

@Composable
private fun ActionButton(emoji: String, label: String, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { },
        modifier = modifier.height(68.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = NavyBlue),
        border = ButtonDefaults.outlinedButtonBorder.copy()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
}