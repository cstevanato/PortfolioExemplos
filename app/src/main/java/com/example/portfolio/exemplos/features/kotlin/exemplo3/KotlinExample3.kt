package com.example.portfolio.exemplos.features.kotlin.exemplo3
// ============================================
// CÓDIGO REFATORADO COM VAL EM NewTask
// ============================================

data class NewTask(val title: String = "", val description: String = "")

enum class BoardColor {
    BLACK, WHITE, GREEN, BLUE
}

class Board {
    var title: String = ""
    var color: BoardColor = BoardColor.BLUE
    var tasks: MutableList<NewTask> = mutableListOf()

    // Versão simplificada com parâmetros diretos
    fun task(title: String, description: String) {
        tasks.add(NewTask(title, description))
    }
}

fun board(init: Board.() -> Unit): Board {
    val board = Board()
    board.init()
    return board
}

fun Example_2_Main() {
    
    val board1 = board {
        title = "Title 2"
        color = BoardColor.BLACK

        task("Task 1", "Description Task 1")
        task("Task 2", "Description Task 2")
        task("Task 3", "Description Task 3")
    }

    println("${board1.title}, ${board1.color}")
    
    // Imprimir todas as tasks
    board1.tasks.forEachIndexed { index, task ->
        println("Task ${index + 1}: ${task.title} - ${task.description}")
    }
}

fun main() {
    Example_2_Main()
}
