package com.example.portfolio.exemplos

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BasicNavigationNavDisplayTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun navDisplay_navigatesToPagination_andBackToHome() {
        composeRule.setContent {
            BasicNavigation()
        }

        // Estamos no Home (MainNavigation -> HomeScreen)
        composeRule.onNodeWithText("Exemplos").assertIsDisplayed()

        // Clique no card do projeto de paginação (via tag ou texto)
        composeRule.onNodeWithTag("item_projects_Example_List_By_Categories_").performClick()

        // Verifica que entrou na tela de Paginação
        // (Essa tela renderiza itens com "title"/"description"; uma asserção simples é o loading aparecer em algum momento
        // ou apenas garantir que o TopAppBar "Exemplos" sumiu)
        composeRule.onNodeWithText("Exemplos").assertDoesNotExist()

        // Volta: como BasicNavigation usa NavDisplay(onBack = { removeLastOrNull() })
        composeRule.activityRule.scenario.onActivity {
            it.onBackPressedDispatcher.onBackPressed()
        }

        // Deve voltar para Home
        composeRule.onNodeWithText("Exemplos").assertIsDisplayed()
    }
}