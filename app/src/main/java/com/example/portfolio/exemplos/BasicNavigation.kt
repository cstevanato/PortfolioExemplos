package com.example.portfolio.exemplos

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.portfolio.exemplos.features.MainNavigation
import com.example.portfolio.exemplos.features.share.ShareNavigation
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun BasicNavigation(
//    viewModel: BasicNavigationViewModel = hiltViewModel()
) {

    val rootBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.MainHome::class, Route.MainHome.serializer())
                    subclass(Route.HomeNavShare::class, Route.HomeNavShare.serializer())
                }
            }
        },
        Route.MainHome
    )


//    val backStack = viewModel.backStack
    NavDisplay(
        backStack = rootBackStack,
        onBack = { rootBackStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.MainHome> {
                MainNavigation {
//                    rootBackStack.remove(Route.MainHome)
                    rootBackStack.add(it)
                }
            }
            entry<Route.HomeNavShare> {
                ShareNavigation {
                    rootBackStack.remove(Route.HomeNavShare)
                    rootBackStack.add(Route.MainHome)
                }
            }
        },
        transitionSpec = {
            ContentTransform(
                slideInHorizontally(initialOffsetX = { it }),
                slideOutHorizontally(targetOffsetX = { -it })
            )
        },
        popTransitionSpec = {
            ContentTransform(
                slideInHorizontally(initialOffsetX = { -it }),
                slideOutHorizontally(targetOffsetX = { it })
            )
        }
    )
}


