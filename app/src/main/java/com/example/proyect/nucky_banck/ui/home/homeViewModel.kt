package com.example.proyect.nucky_banck.ui.home

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    // El estado inicial ahora tomará los 7 millones del modelo
    private val _uiState = MutableStateFlow(HomeModel())
    val uiState: StateFlow<HomeModel> = _uiState.asStateFlow()

    fun loadUserData(cedula: String) {
        _uiState.update { it.copy(isLoading = true, cedula = cedula) }

        val database = FirebaseDatabase.getInstance().getReference("usuarios")

        database.get().addOnSuccessListener { snapshot ->
            var nombreEncontrado = cedula
            var saldoEncontrado = 7000000.0 // Valor por defecto

            for (userSnapshot in snapshot.children) {
                val dbCedula = userSnapshot.child("cedula").value.toString()
                if (dbCedula == cedula) {
                    nombreEncontrado = userSnapshot.child("nombre").value.toString()

                    // Si en Firebase ya existe un saldo guardado, lo usamos
                    val dbSaldo = userSnapshot.child("saldo").value
                    if (dbSaldo != null) {
                        saldoEncontrado = dbSaldo.toString().toDouble()
                    }
                    break
                }
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    nombreUsuario = nombreEncontrado,
                    saldo = saldoEncontrado // Aquí se actualiza la UI
                )
            }
        }.addOnFailureListener {
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}
