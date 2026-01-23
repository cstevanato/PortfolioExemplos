package com.example.portfolio.exemplos.features.flowlayouts

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

val firstFlow: Flow<String> = flow {
    emit("Primeiro")
    emit("Segundo")
    emit("Terceiro")
}

val secondFlow: Flow<String> = flow {

    emit("Quarto")
    emit("Quinto")

}

suspend fun test() {
    println("FlowsTest: Merge")
    val merge = merge(
        firstFlow.onEach { delay(200) },
        secondFlow.onEach { delay(300) }
    ).onEach { println("FlowsTest: $it") }.collect()

    println("FlowsTest: --------------")
    println("FlowsTest: zip")
    val zipped = firstFlow.onEach { delay(200) }
        .zip(secondFlow.onEach { delay(300) }) { a, b -> a to b }

    zipped.onEach { println("FlowsTest: $it") }.collect()

    val numbers = listOf(1, 2, 3, 4, 5)
    val numbersExpand = numbers.flatMap { x -> listOf(x, x + 1) } // [1, 2, 2, 3, 3, 4, 4, 5, 5, 6]

}


suspend fun combine() {
    // Simula o estado de carregamento
    val isLoadingFlow = flowOf(true, false)
    // Simula dados vindos do banco de dados/API
    val dataFlow = flowOf("Dados 1", "Dados 2")

    // Combina os dois fluxos
    val uiStateFlow = isLoadingFlow.combine(dataFlow) { isLoading, data ->
        if (isLoading) "Carregando..." else "Conteúdo: $data"
    }

    // Coleta e imprime os resultados
    uiStateFlow.collect { println("FlowsTest:: $it") }
}

suspend fun zip() {
    println("FlowsTest: Zip")
    // Fluxo 1: Nomes
    val usernames = flowOf("Alice", "Bob", "Charlie")
    // Fluxo 2: Pontuações
    val scores = flowOf(90, 80, 95, 100)

    // Combinando os dois fluxos usando zip
    val leaderboardFlow = usernames.zip(scores) { name, score ->
        "$name scored $score" // Formato de saída
    }

    // Coletando o resultado
    leaderboardFlow.collect { println("FlowsTest: $it") }
    // Saída:
    // Alice scored 90
    // Bob scored 80
    // Charlie scored 95
}

suspend fun mergeflow() {

    println("FlowsTest: merge")
    val flowA = flow {
        for (i in 1..3) {
            delay(300)
            emit("A$i")
        }
    }

    val flowB = flow {
        for (i in 1..3) {
            delay(200)
            emit("B$i")
        }
    }

    val start = System.currentTimeMillis()
    fun ts() = "${System.currentTimeMillis() - start}ms"

    println("FlowsTest: Coletando merge(flowA, flowB)")
    merge(flowA, flowB)
        .onEach { println("FlowsTest: ${ts()} -> $it") }
        .collect()

}

fun callMain() {
    GlobalScope.launch(Dispatchers.Default) {
//        test() // Call the suspend function inside the coroutine builder
//        combine()
//        zip()
        mergeflow()
    }
}
