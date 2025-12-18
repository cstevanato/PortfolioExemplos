package com.example.portfolio.exemplos.features.authentication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.portfolio.exemplos.Route
import com.example.portfolio.exemplos.features.authentication.ui.HomeAuthScreen
import com.example.portfolio.exemplos.features.authentication.ui.LoginScreen
import com.example.portfolio.exemplos.features.authentication.ui.SignUpScreen

@Composable
fun AuthNavigation(modifier: Modifier = Modifier, onNavigateToMainHome: () -> Unit) {

    val authBackStack = rememberNavBackStack(Route.HomeAuth.Home)

    NavDisplay(
        backStack = authBackStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Route.HomeAuth.Home> {
                println("Claudio - HomeAuthScreen")
                HomeAuthScreen(
                    onNavigateToLogin = { authBackStack.add(Route.HomeAuth.Login) },
                )
            }
            entry<Route.HomeAuth.Login> {
                LoginScreen(
                    onNavigateToSignUp = { authBackStack.add(Route.HomeAuth.SignUp) },
                    onNavigateToHome = onNavigateToMainHome
                )
            }
            entry<Route.HomeAuth.SignUp> {
                SignUpScreen(
                    onNavigateToHome = onNavigateToMainHome,
                    onNavigateToLogin = { authBackStack.removeLastOrNull() }
                )
            }
        }
    )
}
