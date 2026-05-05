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

    fun onCedulaChange(newValue: String) {
        _uiState.update { it.copy(cedula = newValue, cedulaError = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, passwordError = null) }
    }

    fun onLoginClicked() {
        if (validateFields()) {
            performLogin()
        }
    }

    private fun validateFields(): Boolean {
        val state = _uiState.value

        val cedulaError = when {
            state.cedula.isBlank() -> "Ingresa tu número de cédula"
            !state.cedula.all { it.isDigit() } -> "La cédula solo debe contener números"
            state.cedula.length < 6 || state.cedula.length > 10 -> "La cédula debe tener entre 6 y 10 dígitos"
            else -> null
        }

        val passwordError = when {
            state.password.isBlank() -> "Ingresa tu contraseña"
            state.password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }

        _uiState.update { it.copy(cedulaError = cedulaError, passwordError = passwordError) }
        return cedulaError == null && passwordError == null
    }

    private fun performLogin() {
        _uiState.update { it.copy(isLoading = true, generalError = null) }

        database = FirebaseDatabase.getInstance().getReference("usuarios")

        database.get().addOnSuccessListener { snapshot ->

            var loginSuccess = false

            for (userSnapshot in snapshot.children) {
                val dbCedula   = userSnapshot.child("cedula").value.toString()
                val dbPassword = userSnapshot.child("password").value.toString()

                if (dbCedula == _uiState.value.cedula && dbPassword == _uiState.value.password) {
                    loginSuccess = true
                    break
                }
            }

            if (loginSuccess) {
                val cedulaIngresada = _uiState.value.cedula
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = true,
                        cedula = cedulaIngresada
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

        }.addOnFailureListener { exception ->
            _uiState.update {
                it.copy(
                    isLoading = false,
                    generalError = "Error de conexión: ${exception.message}"
                )
            }
        }
    }
}