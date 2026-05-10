package com.example.proyect.nucky_banck.ui.login

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginModel())
    val uiState: StateFlow<LoginModel> = _uiState.asStateFlow()

    private lateinit var database: DatabaseReference

    // =========================
    // CAMBIOS EN LOS INPUTS
    // =========================

    fun onCedulaChange(newValue: String) {

        _uiState.update {
            it.copy(
                cedula = newValue,
                cedulaError = null,
                generalError = null
            )
        }
    }

    fun onPasswordChange(newValue: String) {

        _uiState.update {
            it.copy(
                password = newValue,
                passwordError = null,
                generalError = null
            )
        }
    }

    // =========================
    // LOGIN
    // =========================

    fun onLoginClicked() {

        if (validateFields()) {
            login(
                cedula = _uiState.value.cedula,
                password = _uiState.value.password
            )
        }
    }

    // =========================
    // VALIDACIONES
    // =========================

    private fun validateFields(): Boolean {

        val state = _uiState.value

        val cedulaError = when {

            state.cedula.isBlank() ->
                "Ingresa tu número de cédula"

            !state.cedula.all { it.isDigit() } ->
                "La cédula solo debe contener números"

            state.cedula.length < 6 || state.cedula.length > 10 ->
                "La cédula debe tener entre 6 y 10 dígitos"

            else -> null
        }

        val passwordError = when {

            state.password.isBlank() ->
                "Ingresa tu contraseña"

            state.password.length < 6 ->
                "La contraseña debe tener al menos 6 caracteres"

            else -> null
        }

        _uiState.update {
            it.copy(
                cedulaError = cedulaError,
                passwordError = passwordError
            )
        }

        return cedulaError == null && passwordError == null
    }

    // =========================
    // LÓGICA LOGIN FIREBASE
    // (MISMA LÓGICA DEL PRIMER PROYECTO)
    // =========================

    private fun login(
        cedula: String,
        password: String
    ) {

        // Loading
        _uiState.update {
            it.copy(
                isLoading = true,
                generalError = null,
                loginSuccess = false
            )
        }

        // Referencia Firebase
        database = FirebaseDatabase
            .getInstance()
            .getReference("usuarios")

        // MISMA LÓGICA:
        // busca directamente por la cédula
        database.child(cedula)
            .get()

            .addOnSuccessListener { dataUser ->

                // Usuario no existe
                if (!dataUser.exists()) {

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            generalError = "El usuario no existe"
                        )
                    }

                    return@addOnSuccessListener
                }

                // Password BD
                val dbPassword =
                    dataUser.child("password")
                        .value
                        .toString()

                // Comparación password
                if (dbPassword == password) {

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            cedula = cedula
                        )
                    }

                } else {

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            generalError = "Cédula o contraseña incorrecta"
                        )
                    }
                }
            }

            .addOnFailureListener { exception ->

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        generalError = "Error de conexión: ${exception.message}"
                    )
                }
            }
    }
}