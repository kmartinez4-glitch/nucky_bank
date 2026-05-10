package com.example.proyect.nucky_banck.ui.register

data class RegisterModel(

    val fullName: String = "",

    val cedula: String = "",

    val password: String = "",

    val confirmPassword: String = "",

    val isLoading: Boolean = false,

    val fullNameError: String? = null,

    val cedulaError: String? = null,

    val passwordError: String? = null,

    val confirmPasswordError: String? = null,

    val generalError: String? = null,

    val registerSuccess: Boolean = false
)