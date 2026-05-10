package com.example.proyect.nucky_banck.ui.navigation

object NavRoutes {

    const val LOGIN = "login"

    const val REGISTER = "register"

    const val HOME = "home/{cedula}"

    // Ruta dinámica Home
    fun home(cedula: String) = "home/$cedula"
}