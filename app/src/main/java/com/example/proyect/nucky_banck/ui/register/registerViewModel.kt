package com.example.proyect.nucky_banck.ui.register

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterModel())
    val uiState: StateFlow<RegisterModel> = _uiState.asStateFlow()

    private lateinit var database: DatabaseReference

    fun onFullNameChange(value: String) {
        _uiState.update {
            it.copy(
                fullName = value,
                fullNameError = null
            )
        }
    }

    fun onCedulaChange(value: String) {
        _uiState.update {
            it.copy(
                cedula = value,
                cedulaError = null
            )
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(
                password = value,
                passwordError = null
            )
        }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update {
            it.copy(
                confirmPassword = value,
                confirmPasswordError = null
            )
        }
    }

    fun onRegisterClicked() {

        if (validateFields()) {
            registerUser()
        }
    }

    private fun validateFields(): Boolean {

        val state = _uiState.value

        val fullNameError =
            if (state.fullName.isBlank())
                "Ingresa tu nombre"
            else null

        val cedulaError =
            when {
                state.cedula.isBlank() ->
                    "Ingresa tu cédula"

                !state.cedula.all { it.isDigit() } ->
                    "Solo números"

                else -> null
            }

        val passwordError =
            when {
                state.password.isBlank() ->
                    "Ingresa una contraseña"

                state.password.length < 6 ->
                    "Mínimo 6 caracteres"

                else -> null
            }

        val confirmPasswordError =
            when {
                state.confirmPassword.isBlank() ->
                    "Confirma la contraseña"

                state.password != state.confirmPassword ->
                    "Las contraseñas no coinciden"

                else -> null
            }

        _uiState.update {
            it.copy(
                fullNameError = fullNameError,
                cedulaError = cedulaError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
        }

        return fullNameError == null &&
                cedulaError == null &&
                passwordError == null &&
                confirmPasswordError == null
    }

    private fun registerUser() {

        val state = _uiState.value

        _uiState.update {
            it.copy(
                isLoading = true,
                generalError = null
            )
        }

        database = FirebaseDatabase
            .getInstance()
            .getReference("usuarios")

        // verificar si existe
        database.child(state.cedula)
            .get()

            .addOnSuccessListener { snapshot ->

                if (snapshot.exists()) {

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            generalError = "La cédula ya está registrada"
                        )
                    }

                } else {

                    val user = mapOf(
                        "nombre" to state.fullName,
                        "cedula" to state.cedula,
                        "password" to state.password
                    )

                    database.child(state.cedula)
                        .setValue(user)

                        .addOnSuccessListener {

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    registerSuccess = true
                                )
                            }
                        }

                        .addOnFailureListener { exception ->

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    generalError = exception.message
                                )
                            }
                        }
                }
            }

            .addOnFailureListener { exception ->

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        generalError = exception.message
                    )
                }
            }
    }
}