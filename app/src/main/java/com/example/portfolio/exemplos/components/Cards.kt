package com.example.portfolio.exemplos.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme
import com.example.portfolio.exemplos.ui.theme.elevation
import com.example.portfolio.exemplos.ui.theme.spacing


@Composable
fun CardMenu(model: CardModel, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.elevation.medium
        ),
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.card.inside),
        ) {
            Text(text = model.title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.text.between))
            Text(text = model.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
@Preview
fun CardMenuPreview() {
    PortfolioExemplosTheme {
        CardMenu(
            model = CardModel(
                title = "My Project",
                description = "Description Project"
            ),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.card.horizontal)
                .padding(vertical = MaterialTheme.spacing.card.between),
            onClick = {}
        )
    }
}