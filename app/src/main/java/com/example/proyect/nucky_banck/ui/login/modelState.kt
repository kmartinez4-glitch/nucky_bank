package com.example.proyect.nucky_banck.ui.login  // ✅

// LoginUiState.kt
// Este archivo define UNA SOLA clase que describe todo el estado visual del login.
// Usamos 'data class' porque queremos comparar estados fácilmente (si cambió algo, redibuja).


data class LoginModel(

    // El texto que el usuario ha escrito en el campo de cédula
    val cedula: String = "",

    // El texto que el usuario ha escrito en el campo de contraseña
    val password: String = "",

    // ¿Actualmente estamos esperando respuesta del servidor?
    // Cuando es true, mostramos un spinner y desactivamos el botón.
    val isLoading: Boolean = false,

    // Si la cédula tiene un error de validación, aquí va el mensaje.
    // null significa "sin error" — esto es más limpio que un String vacío.
    val cedulaError: String? = null,

    // Lo mismo para la contraseña
    val passwordError: String? = null,

    // Si el login falló por credenciales incorrectas, aquí va el mensaje general
    val generalError: String? = null,

    // ¿El login fue exitoso? La pantalla observará esto para navegar
    val loginSuccess: Boolean = false
)