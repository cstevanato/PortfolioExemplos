package com.example.portfolio.exemplos.features.kotlin.exemplo2

// ============================================
// CÓDIGO REFATORADO COM BUILDER PATTERN
// Mantém sintaxe DSL com blocos
// ============================================

data class NewTask(val title: String = "", val description: String = "")

enum class BoardColor {
    BLACK, WHITE, GREEN, BLUE
}

class TaskBuilder {
    private var title: String = ""
    private var description: String = ""

    fun title(value: String) { 
        title = value 
    }
    
    fun description(value: String) { 
        description = value 
    }
    
    internal fun build() = NewTask(title, description)
}

class Board {
    var title: String = ""
    var color: BoardColor = BoardColor.BLUE
    var tasks: MutableList<NewTask> = mutableListOf()

    fun task(init: TaskBuilder.() -> Unit) {
        val builder = TaskBuilder()
        builder.init()
        tasks.add(builder.build())
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

        task {
            title("Task 1")
            description("Description Task 1")
        }
        task {
            title("Task 2")
            description("Description Task 2")
        }
        task {
            title("Task 3")
            description("Description Task 3")
        }
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
