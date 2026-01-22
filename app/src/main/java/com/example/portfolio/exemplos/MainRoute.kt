package com.example.portfolio.exemplos

import androidx.navigation3.runtime.NavKey
import com.example.portfolio.exemplos.model.ProjectModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object MainHome : Route {
        @Serializable
        data object Home : Route

        @Serializable
        data class Details(val id: String) : Route

        @Serializable
        data object ListByImageRecompositionOptimize : Route

        @Serializable
        data object ListByCategories : Route

        @Serializable
        data object DragAndDropExample : Route

        @Serializable
        data object SearchByQuery : Route

        @Serializable
        data object SearchByState : Route

        @Serializable
        data object PaginationExample : Route

        @Serializable
        data object ConnectivityExample : Route

        @Serializable
        data object SwipeExample : Route

        @Serializable
        data object Media : Route

        @Serializable
        data object ProgressIndicator : Route

        @Serializable
        data object UserErrorHandling : Route

        @Serializable
        data object Address : Route

        @Serializable
        data object ButtonEffectsExample : Route

        @Serializable
        data object Meditation : Route

        @Serializable
        data object VideoForOnboarding : Route

        @Serializable
        data object BottomSheets : Route

        @Serializable
        data object LoadingCustom : Route

        @Serializable
        data object SnakeSquareScreen: Route

        @Serializable
        data object SemiCircleScreen: Route

        @Serializable
        data object AnimatedCircularProgressScreen : Route

        @Serializable
        data object SpendingScreen : Route

        @Serializable
        data object AnimationsListScreen : Route

        @Serializable
        data object AnimationScreen : Route

        @Serializable
        data object TabSwipable : Route

        @Serializable
        data object StableTest : Route

        @Serializable
        data object SliderExample : Route

        @Serializable
        data object AnimatedGradientBackground : Route

        @Serializable
        data object MovingGradientBackground : Route
    }

    @Serializable
    data object HomeNavShare : Route {
        @Serializable
        data object Share : Route

        @Serializable
        data object Register : Route
    }

    @Serializable
    data object HomeAuth : Route {
        @Serializable
        data object Home : Route

        @Serializable
        data object Login : Route

        @Serializable
        data object SignUp : Route
    }

    @Serializable
    data object HomeLayout : Route {
        @Serializable
        data object Home : Route

        @Serializable
        data object FlowLayout : Route

        @Serializable
        data object CascadeLayout : Route

        @Serializable
        data object CircularLayout : Route

        @Serializable
        data object GridLayout : Route

        @Serializable
        data object CustomLayout : Route
    }
}

val projectsStateItems = persistentListOf(
    ProjectModel(
        "Share viewModel and navigation for multiple nodes.",
        "Share viewModel and navigation for multiple nodes.",
        Route.HomeNavShare
    ),
    ProjectModel(
        "Layouts",
        "Layouts",
        Route.HomeLayout
    ),
    ProjectModel(
        name = "Authentication",
        description = "Authentication",
        dest = Route.HomeAuth
    ),
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
    ),
    ProjectModel(
        "Error Handling Example.",
        "Error Handling Example.",
        Route.MainHome.UserErrorHandling
    ),
    ProjectModel(
        "Address Example.",
        "Example of the find address by zip code, with suspend and flow.",
        Route.MainHome.Address
    ),
    ProjectModel(
        "Button Effects Example",
        "Button Effects Example",
        Route.MainHome.ButtonEffectsExample
    ),
    ProjectModel(
        "Meditation App",
        "Meditation App",
        Route.MainHome.Meditation
    ),
    ProjectModel(
        "Video For Onboarding",
        "Video For Onboarding",
        Route.MainHome.VideoForOnboarding
    ),
    ProjectModel(
        "Bottom Sheets",
        "Bottom Sheets",
        Route.MainHome.BottomSheets
    ),
    ProjectModel(
        "Loading Custom",
        "Loading Custom",
        Route.MainHome.LoadingCustom
    ),
    ProjectModel(
        "SnakeSquareScreen",
        "SnakeSquareScreen",
        Route.MainHome.SnakeSquareScreen
    ),
    ProjectModel(
        "SemiCircleScreen",
        "SemiCircleScreen",
        Route.MainHome.SemiCircleScreen
    ),
    ProjectModel(
        "AnimatedCircularProgressScreen",
        "AnimatedCircularProgressScreen",
        Route.MainHome.AnimatedCircularProgressScreen
    ),
    ProjectModel(
        "SpendingScreen",
        "SpendingScreen",
        Route.MainHome.SpendingScreen
    ),
    ProjectModel(
        "AnimationsListScreen",
        "AnimationsListScreen",
        Route.MainHome.AnimationsListScreen
    ),
    ProjectModel(
        "AnimationScreen",
        "AnimationScreen",
        Route.MainHome.AnimationScreen
    ),
    ProjectModel(
        "TabSwipable",
        "TabSwipable",
        Route.MainHome.TabSwipable
    ),
    ProjectModel(
        "StableTest",
        "StableTest",
        Route.MainHome.StableTest
    ),
    ProjectModel(
        "Slider",
        "Slider",
        Route.MainHome.SliderExample
    ),
    ProjectModel(
        "AnimatedGradientBackground",
        "AnimatedGradientBackground",
        Route.MainHome.AnimatedGradientBackground
    ),
    ProjectModel(
        "MovingGradientBackground",
        "MovingGradientBackground",
        Route.MainHome.MovingGradientBackground
    )
)
