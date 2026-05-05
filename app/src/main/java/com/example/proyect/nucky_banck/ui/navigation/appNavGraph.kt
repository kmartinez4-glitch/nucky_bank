package com.example.proyect.nucky_banck.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.proyect.nucky_banck.ui.home.HomeScreen
import com.example.proyect.nucky_banck.ui.login.LoginScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.LOGIN
    ) {

        // ── Pantalla Login ──────────────────────────────
        composable(NavRoutes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { cedula ->
                    // navega a Home y borra Login del historial
                    navController.navigate(NavRoutes.home(cedula)) {
                        popUpTo(NavRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ── Pantalla Home ───────────────────────────────
        composable(
            route = NavRoutes.HOME,
            arguments = listOf(
                navArgument("cedula") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // recupera la cédula que viajó por la ruta
            val cedula = backStackEntry.arguments?.getString("cedula") ?: ""

            HomeScreen(
                cedula = cedula,
                onLogout = {
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(NavRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }
    }
}