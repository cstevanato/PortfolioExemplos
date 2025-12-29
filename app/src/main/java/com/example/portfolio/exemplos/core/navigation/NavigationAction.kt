package com.example.portfolio.exemplos.core.navigation

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import com.example.portfolio.exemplos.Route

// https://www.youtube.com/watch?v=BFhVvAzC52w&t=899s

sealed interface NavigationAction {
    data class Navigate(
        val destination: Route,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ): NavigationAction

    data object NavigateUp: NavigationAction
}