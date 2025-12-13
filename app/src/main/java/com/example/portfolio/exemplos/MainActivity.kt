package com.example.portfolio.exemplos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.portfolio.exemplos.features.errorHandling.ui.UserErrorHandlingScreen
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PortfolioExemplosTheme {
                UserErrorHandlingScreen()
//                BasicNavigation()
            }
        }
    }
}
