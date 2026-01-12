package com.example.portfolio.exemplos.features.kotlinConf

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

val alphabets = flowOf("A", "B", "C", "D").onEach {
    delay(500)
}
val numbers = flowOf(1, 2, 3).onEach {
    delay(1000)
}

fun flowMage() {
    runBlocking {
        merge(alphabets, numbers)
            .onEach {
                println("Claudio: $it")
            }.collect { value -> println("Claudio: Collected $value") }
    }
}

fun flowZip() {
    runBlocking {
        alphabets.zip(numbers) { a, b ->
            "Claudio: $a -> $b"
        }.collect {
            println(it)
        }

    }
}

fun flowCombine1() {
    runBlocking {

        alphabets.combine(numbers) { a, b ->
            "Claudio: $a -> $b"
        }.collect {
            println(it)
        }

    }
}

fun flowCombine2() {
    runBlocking {
        combine(alphabets, alphabets, numbers) { a, b, c ->
            "Claudio: $a -> $b -> $c"
        }.collect {
            println(it)
        }
    }
}