package com.example.proyect.nucky_banck.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyect.nucky_banck.ui.home.HomeScreen
import com.example.proyect.nucky_banck.ui.login.LoginScreen
import com.example.proyect.nucky_banck.ui.register.RegisterScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.LOGIN
    ) {

        // LOGIN
        composable(
            route = NavRoutes.LOGIN
        ) {

            LoginScreen(

                navController = navController,

                onLoginSuccess = { cedula ->

                    navController.navigate(
                        NavRoutes.home(cedula)
                    ) {

                        popUpTo(NavRoutes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // REGISTER
        composable(
            route = NavRoutes.REGISTER
        ) {

            RegisterScreen(
                navController = navController
            )
        }

        // HOME
        composable(
            route = NavRoutes.HOME,

            arguments = listOf(
                navArgument("cedula") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val cedula =
                backStackEntry.arguments
                    ?.getString("cedula")
                    ?: ""

            HomeScreen(

                cedula = cedula,

                onLogout = {

                    navController.navigate(
                        NavRoutes.LOGIN
                    ) {

                        popUpTo(0)
                    }
                }
            )
        }
    }
}