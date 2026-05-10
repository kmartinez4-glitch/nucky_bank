package com.example.proyect.nucky_banck.ui.login

data class LoginModel(

    val cedula: String = "",

    val password: String = "",

    val isLoading: Boolean = false,

    val cedulaError: String? = null,

    val passwordError: String? = null,

    val generalError: String? = null,

    val loginSuccess: Boolean = false
)