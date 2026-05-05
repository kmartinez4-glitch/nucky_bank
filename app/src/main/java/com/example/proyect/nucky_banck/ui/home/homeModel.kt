package com.example.proyect.nucky_banck.ui.home

data class HomeModel(
    val cedula: String = "",
    val nombreUsuario: String = "",
    val saldo: Double = 0.0,
    val isLoading: Boolean = false
)