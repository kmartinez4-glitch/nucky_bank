package com.example.proyect.nucky_banck.ui.home

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeModel())
    val uiState: StateFlow<HomeModel> = _uiState.asStateFlow()

    fun loadUserData(cedula: String) {
        _uiState.update { it.copy(isLoading = true, cedula = cedula) }

        val database = FirebaseDatabase.getInstance().getReference("usuarios")

        database.get().addOnSuccessListener { snapshot ->
            var nombreEncontrado = cedula // fallback: si no encuentra el nombre muestra la cédula

            for (userSnapshot in snapshot.children) {
                val dbCedula = userSnapshot.child("cedula").value.toString()
                if (dbCedula == cedula) {
                    nombreEncontrado = userSnapshot.child("nombre").value.toString()
                    break
                }
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    nombreUsuario = nombreEncontrado
                )
            }
        }.addOnFailureListener {
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}