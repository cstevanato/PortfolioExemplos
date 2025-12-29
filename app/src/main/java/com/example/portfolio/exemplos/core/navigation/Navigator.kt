package com.example.portfolio.exemplos.core.navigation

import androidx.navigation.NavOptionsBuilder
import com.example.portfolio.exemplos.Route
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface Navigator {
    val startDestination: Route
    val navigationAction: Flow<NavigationAction>

    suspend fun navigate(
        destination: Route,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigateUp()
}

class DefaultNavigator(
    override val startDestination: Route
) : Navigator {

    private val _navigationAction = Channel<NavigationAction>()
    override val navigationAction = _navigationAction.receiveAsFlow()

    override suspend fun navigate(
        destination: Route,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _navigationAction.send(NavigationAction.Navigate(destination, navOptions))
    }

    override suspend fun navigateUp() {
        _navigationAction.send(NavigationAction.NavigateUp)

    }
}