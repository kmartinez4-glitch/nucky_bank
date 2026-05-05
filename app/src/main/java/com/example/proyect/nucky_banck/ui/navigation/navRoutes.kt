package com.example.proyect.nucky_banck.ui.navigation

object NavRoutes {
    const val LOGIN = "login"
    const val HOME  = "home/{cedula}"  // 👈 la cédula viaja como parámetro

    // helper para construir la ruta con el valor real
    fun home(cedula: String) = "home/$cedula"
}