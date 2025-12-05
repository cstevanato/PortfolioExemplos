package com.example.portfolio.exemplos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.portfolio.exemplos.features.Search.SearchBarByQueryScreen
import com.example.portfolio.exemplos.features.Search.SearchBarByStateScreen
import com.example.portfolio.exemplos.features.dragdrop.DragAndDropBoxes
import com.example.portfolio.exemplos.features.list.ListByCategoriesScreen
import com.example.portfolio.exemplos.features.list.ListByImageRecompositionOptimizeScreen
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
    data object ListByImageRecompositionOptimize : Dest()

    @Serializable
    data object ListByCategories : Dest()

    @Serializable
    data object DragAndDropExample : Dest()

    @Serializable
    data object SearchByQuery : Dest()

    @Serializable
    data object SearchByState : Dest()

}

@Composable
fun EntryProviderScope<Dest>.Route(
    backStack: SnapshotStateList<Dest>
) {
    entry<Dest.Home> {
        HomeScreen() {
            backStack.add(it)
        }
    }
    entry<Dest.Details> { key -> DetailsScreen(key.id) }
    entry<Dest.ListByImageRecompositionOptimize> { ListByImageRecompositionOptimizeScreen() }
    entry<Dest.DragAndDropExample> { DragAndDropBoxes() }
    entry<Dest.SearchByQuery> { SearchBarByQueryScreen() }
    entry<Dest.SearchByState> { SearchBarByStateScreen() }
    entry<Dest.ListByCategories> { ListByCategoriesScreen() }
}


val projectsStateItems = persistentListOf(
    ProjectModel(
        "Example of passing parameters.",
        "Example of passing parameters between screens using navigation 3.",
        Dest.Details("Example of passing parameters.")

    ),
    ProjectModel(
        "Example List Simple With Image.",
        "Example List Simple  With Image recompose optimize.",
        Dest.ListByImageRecompositionOptimize
    ),
    ProjectModel(
        "Example List By Categories.",
        "Example List By Categories",
        Dest.ListByCategories
    ),
    ProjectModel(
        "Example SearchByQuery by Query.",
        "Example SearchByQuery by Query with material 3.",
        Dest.SearchByQuery
    ),
    ProjectModel(
        "Example SearchByQuery by State.",
        "Example SearchByQuery by State with material 3.",
        Dest.SearchByState
    )
)
