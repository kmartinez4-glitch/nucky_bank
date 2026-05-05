package com.example.proyect.nucky_banck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.proyect.nucky_banck.ui.navigation.AppNavGraph
import com.example.proyect.nucky_banck.ui.theme.NuckybanckTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NuckybanckTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController) // 👈 arranca la navegación
            }
        }
    }
}