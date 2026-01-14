package com.example.portfolio.exemplos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.portfolio.exemplos.features.animation.AnimatedCircularProgressScreen
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme
import dagger.hilt.android.AndroidEntryPoint

@kotlinx.serialization.Serializable
data object ScreenA : NavKey

@kotlinx.serialization.Serializable
data object ScreenB : NavKey

@kotlinx.serialization.Serializable
data object ScreenC : NavKey

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//+        test01()
        setContent {
            PortfolioExemplosTheme {
//                AnimatedCircularProgressIndicatorScreen()
                AnimatedCircularProgressScreen()
//                ExampleScreen()
//                AnimationsListScreen()
//                AnimationScreen()
//                TabSwipable()
//                BasicNavigation()
//                NavigateTest()
            }
        }
    }

}

@Composable
private fun NavigateTest() {
    Scaffold { paddingValues ->

        val backStack = rememberNavBackStack(ScreenA)

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<ScreenA> {
                    ContentOrange("This is Screen A") {
                        Button(onClick = { backStack.add(ScreenB) }) {
                            Text("Go to Screen B")
                        }
                    }
                }
                entry<ScreenB> {
                    ContentMauve("This is Screen B") {
                        Button(onClick = { backStack.add(ScreenC) }) {
                            Text("Go to Screen C")
                        }
                    }
                }
                entry<ScreenC>(
                    metadata = NavDisplay.transitionSpec {
                        // Slide new content up, keeping the old content in place underneath
                        slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(1000)
                        ) togetherWith ExitTransition.KeepUntilTransitionsFinished
                    } + NavDisplay.popTransitionSpec {
                        // Slide old content down, revealing the new content in place underneath
                        EnterTransition.None togetherWith
                                slideOutVertically(
                                    targetOffsetY = { it },
                                    animationSpec = tween(1000)
                                )
                    } + NavDisplay.predictivePopTransitionSpec {
                        // Slide old content down, revealing the new content in place underneath
                        EnterTransition.None togetherWith
                                slideOutVertically(
                                    targetOffsetY = { it },
                                    animationSpec = tween(1000)
                                )
                    }
                ) {
                    ContentGreen("This is Screen C")
                }
            },
            transitionSpec = {
                // Slide in from right when navigating forward
                slideInHorizontally(initialOffsetX = { it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { -it })
            },
            popTransitionSpec = {
                // Slide in from left when navigating back
                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
            },
            predictivePopTransitionSpec = {
                // Slide in from left when navigating back
                slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ContentOrange(string: String,  content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Yellow), contentAlignment = Alignment.Center ) {
        Column() {
            Text(text = string)
            content()
        }
    }
}

@Composable
fun ContentMauve(string: String,  content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Red), contentAlignment = Alignment.Center ) {
        Column() {
            Text(text = string)
            content()
        }
    }
}

@Composable
fun ContentGreen(string: String) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Green), contentAlignment = Alignment.Center ) {
        Column() {
            Text(text = string)
        }
    }
}

