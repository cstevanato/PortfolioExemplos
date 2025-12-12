package com.example.portfolio.exemplos

import androidx.navigation3.runtime.NavKey
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

        @Serializable
        data object PaginationExample : Route, NavKey

        @Serializable
        data object ConnectivityExample : Route, NavKey

        @Serializable
        data object SwipeExample : Route, NavKey

        @Serializable
        data object Media : Route, NavKey

        @Serializable
        data object ProgressIndicator : Route, NavKey
    }

    @Serializable
    data object HomeNavShare : Route, NavKey {
        @Serializable
        data object Share : Route, NavKey

        @Serializable
        data object Register : Route, NavKey
    }
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
    ProjectModel(
        "Pagination Example.",
        "Pagination Example.",
        Route.MainHome.PaginationExample
    ),
    ProjectModel(
        "Connectivity Example.",
        "Connectivity Example.",
        Route.MainHome.ConnectivityExample
    ),
    ProjectModel(
        "Swipe Example.",
        "Swipe Example.",
        Route.MainHome.SwipeExample
    ),
    ProjectModel(
        "Media Example.",
        "Media with permission compose Example.",
        Route.MainHome.Media
    ),
    ProjectModel(
        "Progress Indicator Example.",
        "Progress Indicator Example.",
        Route.MainHome.ProgressIndicator
    )
)
