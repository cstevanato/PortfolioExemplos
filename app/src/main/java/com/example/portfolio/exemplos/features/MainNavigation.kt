package com.example.portfolio.exemplos.features

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.portfolio.exemplos.HomeScreen
import com.example.portfolio.exemplos.Route
import com.example.portfolio.exemplos.features.Search.SearchBarByQueryScreen
import com.example.portfolio.exemplos.features.Search.SearchBarByStateScreen
import com.example.portfolio.exemplos.features.dragdrop.DragAndDropBoxes
import com.example.portfolio.exemplos.features.list.ListByCategoriesScreen
import com.example.portfolio.exemplos.features.list.ListByImageRecompositionOptimizeScreen
import com.example.portfolio.exemplos.features.parameters.DetailsScreen
import com.example.portfolio.exemplos.features.parameters.DetailsViewModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    onNavigationTo: (Route) -> Unit
) {
    val mainBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.MainHome.Home::class, Route.MainHome.Home.serializer())
                    subclass(Route.MainHome.Details::class, Route.MainHome.Details.serializer())
                    subclass(
                        Route.MainHome.ListByImageRecompositionOptimize::class,
                        Route.MainHome.ListByImageRecompositionOptimize.serializer()
                    )
                    subclass(
                        Route.MainHome.ListByCategories::class,
                        Route.MainHome.ListByCategories.serializer()
                    )
                    subclass(
                        Route.MainHome.DragAndDropExample::class,
                        Route.MainHome.DragAndDropExample.serializer()
                    )
                    subclass(
                        Route.MainHome.SearchByQuery::class,
                        Route.MainHome.SearchByQuery.serializer()
                    )
                    subclass(
                        Route.MainHome.SearchByState::class,
                        Route.MainHome.SearchByState.serializer()
                    )
                }
            }
        },
        Route.MainHome.Home
    )

    NavDisplay(
        backStack = mainBackStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Route.MainHome.Home> {
                HomeScreen {
                    if (it is Route.HomeNavShare)
                        onNavigationTo(it)
                    else
                        mainBackStack.add(it)
                }
            }
            entry<Route.MainHome.Details> { key ->
                val viewModel: DetailsViewModel = hiltViewModel(
                    creationCallback = { factory: DetailsViewModel.DetailsViewModelFactory ->
                        factory.create(key.id)
                    }
                )
                DetailsScreen(viewModel)
            }
            entry<Route.MainHome.ListByImageRecompositionOptimize> { ListByImageRecompositionOptimizeScreen() }
            entry<Route.MainHome.DragAndDropExample> { DragAndDropBoxes() }
            entry<Route.MainHome.SearchByQuery> { SearchBarByQueryScreen() }
            entry<Route.MainHome.SearchByState> { SearchBarByStateScreen() }
            entry<Route.MainHome.ListByCategories> { ListByCategoriesScreen() }
        }
    )
}