package com.example.portfolio.exemplos.features.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val names = listOf(
    "Ana Silva", "Bruno Santos", "Carla Oliveira", "Diego Pereira", "Elisa Costa",
    "Fernando Almeida", "Gabriela Rocha", "Henrique Martins", "Isabela Rodrigues", "João Carvalho",
    "Karina Gomes", "Lucas Fernandes", "Mariana Azevedo", "Nelson Barros", "Olivia Castro",
    "Paulo Ribeiro", "Queila Mendes", "Rafael Moura", "Sílvia Duarte", "Tiago Farias",
    "Úrsula Nogueira", "Vitor Andrade", "William Cardoso", "Xênia Pacheco", "Yara Cunha",
    "Zeca Moreira", "Alice Sousa", "Bernardo Correia", "Camila Braga", "Daniel Teixeira",
    "Eduardo Pires", "Flávia Mattos", "Gustavo Brito", "Helena Siqueira", "Igor Freitas",
    "Júlia Sales", "Kleber Coelho", "Larissa Maia", "Mateus Aragão",
    "Nicole Bastos", "Otávio Rezende", "Priscila Sampaio", "Renato Queiroz", "Sara Drumond",
    "Tânia Vieira", "Ubirajara Monteiro", "Vanessa Porto", "Wagner Batista", "Xavier Lopes",
    "Yuri Antunes", "Zilda Fagundes", "Adriana Teles", "Beto Amorim", "Cristina Paiva",
    "Davi Rezende", "Estela Mourão", "Fábio Nunes", "Georgia Dias", "Hugo Serpa",
    "Íris Prata", "Jamila Peixoto", "Kauan Barreto", "Luana Silveira", "Marcelo Sampaio",
    "Natália Furtado", "Orlando Seixas", "Patrícia Funchal", "Quésia Ramos", "Rodrigo Lemos",
    "Samara Quadros", "Talita Bernardino", "Ubiraci Pacheco", "Vânia Simões", "Wesley Vilar",
    "Ximena Alencar", "Ygor Medina", "Zoraide Corrêa", "Afonso Guedes", "Beatriz Lopes",
    "César Valadares", "Daiane Moura", "Emanuel Paes", "Fernanda Ribeiro", "Guilherme Cunha",
    "Heloísa Martins", "Ingrid Souza", "Jonas Fontes", "Kelly Sarmento", "Leonardo Brites",
    "Mirela Couto", "Norberto Severo", "Pedro Braga", "Rita Monteiro", "Sandro Barcellos",
    "Tatiane Ramos", "Victor Maia", "Wellington Tavares", "Yasmin Dantas", "Zé Renato"
).groupBy {
    it.first()
}.toSortedMap()


data class Category(
    val name: String,
    val items: List<String>
)

@Composable
fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    )
}

@Composable
fun CategoryItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 14.sp,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    )
}

@Composable
private fun CategorizedLazyColumn(
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        categories.forEach { category ->
            stickyHeader {
                CategoryHeader(text = category.name)
            }
            items(items = category.items) {
                CategoryItem(text = it)
            }
        }
    }
}

@Composable
fun ListByCategoriesScreen() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        val nameList = names.map {
            Category(it.key.toString(), it.value)
        }

        CategorizedLazyColumn(
            categories = nameList,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}



