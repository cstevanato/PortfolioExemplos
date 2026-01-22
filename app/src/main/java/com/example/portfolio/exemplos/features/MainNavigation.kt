package com.example.portfolio.exemplos.features

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.portfolio.exemplos.HomeScreen
import com.example.portfolio.exemplos.Route
import com.example.portfolio.exemplos.features.animation.AnimatedCircularProgressScreen
import com.example.portfolio.exemplos.features.animation.AnimatedGradientBackgroundCore
import com.example.portfolio.exemplos.features.animation.AnimationScreen
import com.example.portfolio.exemplos.features.animation.AnimationsListScreen
import com.example.portfolio.exemplos.features.animation.MovingGradientBackgroundCore
import com.example.portfolio.exemplos.features.animation.SemiCircleScreen
import com.example.portfolio.exemplos.features.animation.SnakeSquareScreen
import com.example.portfolio.exemplos.features.animation.SpendingScreen
import com.example.portfolio.exemplos.features.bottomsheets.BottomSheetsScreen
import com.example.portfolio.exemplos.features.buttons.ButtonEffectScreen
import com.example.portfolio.exemplos.features.connectivity.ConnectivityScreen
import com.example.portfolio.exemplos.features.dragdrop.DragAndDropBoxes
import com.example.portfolio.exemplos.features.errorHandling.ui.UserErrorHandlingScreen
import com.example.portfolio.exemplos.features.flowTesting.ui.AddressScreen
import com.example.portfolio.exemplos.features.list.ListByCategoriesScreen
import com.example.portfolio.exemplos.features.list.ListByImageRecompositionOptimizeScreen
import com.example.portfolio.exemplos.features.loading.LoadingConcatScreen
import com.example.portfolio.exemplos.features.media.MediaScreen
import com.example.portfolio.exemplos.features.meditation.MeditationScreen
import com.example.portfolio.exemplos.features.pagination.ui.PaginationScreen
import com.example.portfolio.exemplos.features.parameters.DetailsScreen
import com.example.portfolio.exemplos.features.parameters.DetailsViewModel
import com.example.portfolio.exemplos.features.progress.LoadingIndicatorScreen
import com.example.portfolio.exemplos.features.search.SearchBarByQueryScreen
import com.example.portfolio.exemplos.features.search.SearchBarByStateScreen
import com.example.portfolio.exemplos.features.slider.SliderScreen
import com.example.portfolio.exemplos.features.stableImmutable.StableScreen
import com.example.portfolio.exemplos.features.swipe.SwipeScreen
import com.example.portfolio.exemplos.features.tab.TabSwipable
import com.example.portfolio.exemplos.features.videos.VideosForOnboardingScreen


@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    onNavigationTo: (Route) -> Unit
) {
    val mainBackStack = rememberNavBackStack(Route.MainHome.Home)
//        configuration = SavedStateConfiguration {
//            serializersModule = SerializersModule {
//                polymorphic(NavKey::class) {
//                    subclass(Route.MainHome.Home::class, Route.MainHome.Home.serializer())
//                    subclass(Route.MainHome.Details::class, Route.MainHome.Details.serializer())
//                    subclass(
//                        Route.MainHome.ListByImageRecompositionOptimize::class,
//                        Route.MainHome.ListByImageRecompositionOptimize.serializer()
//                    )
//                    subclass(
//                        Route.MainHome.ListByCategories::class,
//                        Route.MainHome.ListByCategories.serializer()
//                    )
//                    subclass(
//                        Route.MainHome.DragAndDropExample::class,
//                        Route.MainHome.DragAndDropExample.serializer()
//                    )
//                    subclass(
//                        Route.MainHome.SearchByQuery::class,
//                        Route.MainHome.SearchByQuery.serializer()
//                    )
//                    subclass(
//                        Route.MainHome.SearchByState::class,
//                        Route.MainHome.SearchByState.serializer()
//                    )
//                    subclass(
//                        Route.MainHome.PaginationExample::class,
//                        Route.MainHome.PaginationExample.serializer()
//                    )
//                }
//            }
//        },
//        Route.MainHome.Home
//    )

    NavDisplay(
        backStack = mainBackStack,
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Route.MainHome.Home> {
                HomeScreen {
                    when (it) {
                        Route.HomeAuth,
                        Route.HomeNavShare,
                        Route.HomeLayout -> {
                            onNavigationTo(it)
                        }

                        else -> mainBackStack.add(it)
                    }
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
            entry<Route.MainHome.PaginationExample> { PaginationScreen() }
            entry<Route.MainHome.ConnectivityExample> { ConnectivityScreen() }
            entry<Route.MainHome.SwipeExample> { SwipeScreen() }
            entry<Route.MainHome.Media> { MediaScreen() }
            entry<Route.MainHome.ProgressIndicator> { LoadingIndicatorScreen() }
            entry<Route.MainHome.UserErrorHandling> { UserErrorHandlingScreen() }
            entry<Route.MainHome.Address> { AddressScreen() }
            entry<Route.MainHome.ButtonEffectsExample> { ButtonEffectScreen() }
            entry<Route.MainHome.Meditation> { MeditationScreen() }
            entry<Route.MainHome.VideoForOnboarding> { VideosForOnboardingScreen() }
            entry<Route.MainHome.BottomSheets> { BottomSheetsScreen() }
            entry<Route.MainHome.LoadingCustom> {
                LoadingConcatScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
            entry<Route.MainHome.SnakeSquareScreen> { SnakeSquareScreen() }
            entry<Route.MainHome.SemiCircleScreen> { SemiCircleScreen() }
            entry<Route.MainHome.AnimatedCircularProgressScreen> {
                AnimatedCircularProgressScreen()
            }
            entry<Route.MainHome.SpendingScreen> { SpendingScreen() }
            entry<Route.MainHome.AnimationsListScreen> { AnimationsListScreen() }
            entry<Route.MainHome.AnimationScreen> { AnimationScreen() }
            entry<Route.MainHome.TabSwipable> { TabSwipable() }
            entry<Route.MainHome.StableTest> { StableScreen() }
            entry<Route.MainHome.SliderExample> { SliderScreen() }
            entry<Route.MainHome.AnimatedGradientBackground> {
                AnimatedGradientBackgroundCore(modifier = Modifier.fillMaxSize())
            }
            entry<Route.MainHome.MovingGradientBackground> {
                MovingGradientBackgroundCore(modifier = Modifier.fillMaxSize())
            }

        },
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
        },
        popTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith
                    slideOutHorizontally { it } + fadeOut()
        },
        predictivePopTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith
                    slideOutHorizontally { it } + fadeOut()
        },
    )
}