package com.example.portfolio.exemplos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.portfolio.exemplos.features.Search.SearchBarByQueryScreen
import com.example.portfolio.exemplos.features.Search.SearchBarByStateScreen
import com.example.portfolio.exemplos.features.dragdrop.DragAndDropBoxes
import com.example.portfolio.exemplos.features.list.ListByCategoriesScreen
import com.example.portfolio.exemplos.features.list.ListByImageRecompositionOptimizeScreen
import com.example.portfolio.exemplos.features.parameters.DetailsScreen
import com.example.portfolio.exemplos.features.parameters.DetailsViewModel
import com.example.portfolio.exemplos.model.ProjectModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object MainHome : Route, NavKey {
        @Serializable
        data object Home : Route, NavKey

        @Serializable
        data class Details(val id: String) : Route, NavKey

        @Serializable
        data object ListByImageRecompositionOptimize : Route, NavKey

        @Serializable
        data object ListByCategories : Route, NavKey

        @Serializable
        data object DragAndDropExample : Route, NavKey

        @Serializable
        data object SearchByQuery : Route, NavKey

        @Serializable
        data object SearchByState : Route, NavKey

    }

    @Serializable
    data object HomeNavShare : Route, NavKey {
        @Serializable
        data object Share : Route, NavKey

        @Serializable
        data object Register : Route, NavKey
    }
}


//
//sealed class Dest : NavKey {
//    @Serializable
//    data object HomeNavShare : Dest() {
//        @Serializable
//        data object Login : Dest()
//
//        @Serializable
//        data object Register : Dest()
//    }
//
//    @Serializable
//    data object MainHome : Dest() {
//        @Serializable
//        data object Home : Dest()
//
//        @Serializable
//        data class Details(val id: String) : Dest()
//
//        @Serializable
//        data object ListByImageRecompositionOptimize : Dest()
//
//        @Serializable
//        data object ListByCategories : Dest()
//
//        @Serializable
//        data object DragAndDropExample : Dest()
//
//        @Serializable
//        data object SearchByQuery : Dest()
//
//        @Serializable
//        data object SearchByState : Dest()
//
//    }
//}

@Composable
fun EntryProviderScope<Route>.Route(
    backStack: SnapshotStateList<Route>
) {
    entry<Route.MainHome.Home> {
        HomeScreen() {
            backStack.add(it)
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

val projectsStateItems = persistentListOf(
    ProjectModel(
        "Example of a route with parameter passing and viewModel with parameter too",
        "Example of a route with parameter passing, viewModel initialization with Hilt receiving a parameter.",
        Route.MainHome.Details("Example of passing parameters.")

    ),
    ProjectModel(
        "Example List Simple With Image.",
        "Example List Simple  With Image recompose optimize.",
        Route.MainHome.ListByImageRecompositionOptimize
    ),
    ProjectModel(
        "Example List By Categories.",
        "Example List By Categories",
        Route.MainHome.ListByCategories
    ),
    ProjectModel(
        "Example SearchByQuery by Query.",
        "Example SearchByQuery by Query with material 3.",
        Route.MainHome.SearchByQuery
    ),
    ProjectModel(
        "Example SearchByQuery by State.",
        "Example SearchByQuery by State with material 3.",
        Route.MainHome.SearchByState
    ),
    ProjectModel(
        "Share viewModel and navigation for multiple nodes.",
        "Share viewModel and navigation for multiple nodes.",
        Route.HomeNavShare

    ),
)
