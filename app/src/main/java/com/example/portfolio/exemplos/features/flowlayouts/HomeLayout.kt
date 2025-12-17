package com.example.portfolio.exemplos.features.flowlayouts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.portfolio.exemplos.HomeScreen
import com.example.portfolio.exemplos.Route
import com.example.portfolio.exemplos.model.ProjectModel
import kotlinx.collections.immutable.persistentListOf


@Composable
fun LayoutsNavigation(
    modifier: Modifier = Modifier,
//    onNavigateToMainHome: () -> Unit
) {
    val mainBackStack = rememberNavBackStack(Route.HomeLayout.Home)
//        configuration = SavedStateConfiguration {
//            serializersModule = SerializersModule {
//                polymorphic(NavKey::class) {
//                    subclass(Route.HomeLayout.Home::class, Route.HomeLayout.Home.serializer())
//                    subclass(
//                        Route.HomeLayout.FlowLayout::class,
//                        Route.HomeLayout.FlowLayout.serializer()
//                    )
//                    subclass(
//                        Route.HomeLayout.CascadeLayout::class,
//                        Route.HomeLayout.CascadeLayout.serializer()
//                    )
//                    subclass(
//                        Route.HomeLayout.CircularLayout::class,
//                        Route.HomeLayout.CircularLayout.serializer()
//                    )
//                    subclass(
//                        Route.HomeLayout.GridLayout::class,
//                        Route.HomeLayout.GridLayout.serializer()
//                    )
//                    subclass(
//                        Route.HomeLayout.CustomLayout::class,
//                        Route.HomeLayout.CustomLayout.serializer()
//                    )
//                }
//            }
//        },
//        Route.HomeLayout.Home
//    )

    NavDisplay(
        backStack = mainBackStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Route.HomeLayout.Home> {
                HomeScreen(state = projectsLayoutsStateItems) {
                    mainBackStack.add(it)
                }
            }
            entry<Route.HomeLayout.GridLayout> {
                GridExample()
            }
            entry<Route.HomeLayout.CircularLayout> {
                CircularExample()
            }
            entry<Route.HomeLayout.CascadeLayout> {
                CascadeExample()
            }
            entry<Route.HomeLayout.FlowLayout> {
                FlowRowExample()
            }
            entry<Route.HomeLayout.CustomLayout> {
                CustomColumnExample()
            }
        }
    )
}

private val projectsLayoutsStateItems = persistentListOf(
    ProjectModel(
        "Example Layout Cascade",
        "Example Layout Cascade.",
        Route.HomeLayout.CascadeLayout

    ),
    ProjectModel(
        "Example Layout Circular",
        "Example Layout Circular.",
        Route.HomeLayout.CircularLayout
    ),
    ProjectModel(
        "Example Layout Flow",
        "Example Layout Flow.",
        Route.HomeLayout.FlowLayout
    ),
    ProjectModel(
        "Example Layout Grid",
        "Example Layout Grid.",
        Route.HomeLayout.GridLayout
    ),
    ProjectModel(
        "Example Layout Custom",
        "Example Layout Custom.",
        Route.HomeLayout.CustomLayout
    )
)
