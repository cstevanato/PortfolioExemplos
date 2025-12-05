package com.example.portfolio.exemplos

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.portfolio.exemplos.features.dragdrop.DragAndDropBoxes
import com.example.portfolio.exemplos.features.list.ListExampleScreen
import com.example.portfolio.exemplos.model.ProjectModel
import com.example.portfolio.exemplos.parameters.DetailsScreen
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

sealed class Dest : NavKey {
    @Serializable
    data object Home : Dest()

    @Serializable
    data class Details(val id: String) : Dest()

    @Serializable
    data object ListsExample : Dest()

    @Serializable
    data object DragAndDropExample : Dest()

}

@Composable
fun EntryProviderScope<Dest>.Route(
    paddingValues: PaddingValues,
    backStack: SnapshotStateList<Dest>
) {
    entry<Dest.Home> {
        HomeScreen(paddingValues = paddingValues) {
            backStack.add(it)
        }
    }
    entry<Dest.Details> { key -> DetailsScreen(key.id) }
    entry<Dest.ListsExample> { ListExampleScreen(modifier = Modifier.padding(paddingValues)) }
    entry<Dest.DragAndDropExample> { DragAndDropBoxes(modifier = Modifier.padding(paddingValues)) }
}


val projectsStateItems = persistentListOf(
    ProjectModel(
        "Example of passing parameters.",
        "Example of passing parameters between screens using navigation 3.",
        Dest.Details("Example of passing parameters.")

    ),
    ProjectModel(
        "Example List With Image.",
        "Example List With Image recompose optimize.",
        Dest.ListsExample
    ),
    ProjectModel(
        "Example Drag and Drop.",
        "Example Drag and Drop.",
        Dest.DragAndDropExample
    )
)
