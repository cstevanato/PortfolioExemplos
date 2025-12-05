package com.example.portfolio.exemplos

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.portfolio.exemplos.components.CardMenu
import com.example.portfolio.exemplos.components.CardModel
import com.example.portfolio.exemplos.model.ProjectModel
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme
import com.example.portfolio.exemplos.ui.theme.spacing
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/// https://www.youtube.com/watch?v=Z0iHRWu09J4&list=WL&index=289&t=12s
//@TraceRecomposition("HomeScreen")
@Composable
fun HomeScreen(
    state: ImmutableList<ProjectModel> = projectsStateItems,
    paddingValues: PaddingValues,
    onClick: (Dest) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        items(
            count = state.size,
            key = { index -> state[index].name }
        ) { index ->
            val project = state[index]
            CardMenu(
                model = CardModel(project.name, project.description),
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.card.horizontal)
                    .padding(vertical = MaterialTheme.spacing.card.between),
            ) {
                onClick.invoke(project.dest)
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    PortfolioExemplosTheme {
        HomeScreen(paddingValues = PaddingValues())
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun HomeScreenDarkPreview() {
    PortfolioExemplosTheme {
        HomeScreen(paddingValues = PaddingValues())
    }
}