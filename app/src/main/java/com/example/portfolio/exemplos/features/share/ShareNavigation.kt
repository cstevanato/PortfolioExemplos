package com.example.portfolio.exemplos.features.share

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.portfolio.exemplos.Route
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


@Composable
fun ShareNavigation(modifier: Modifier = Modifier, onNavigateToMainHome: () -> Unit) {
    val mainBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.HomeNavShare.Share::class, Route.HomeNavShare.Share.serializer())
                    subclass(
                        Route.HomeNavShare.Register::class,
                        Route.HomeNavShare.Register.serializer()
                    )
                }
            }
        },
        Route.HomeNavShare.Share
    )

    val sharedViewModel: ShareViewModel = hiltViewModel()

    NavDisplay(
        backStack = mainBackStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Route.HomeNavShare.Share> {
                ShareHomeScreen(
                    sharedViewModel = sharedViewModel,
                    onNavigateToMainHome = onNavigateToMainHome,
                    onNavigateToShareRegister = {
                        mainBackStack.add(Route.HomeNavShare.Register)
                    }
                )
            }
            entry<Route.HomeNavShare.Register> { key ->
                ShareRegisterScreen(
                    sharedViewModel = sharedViewModel,
                )
            }
        }
    )
}
