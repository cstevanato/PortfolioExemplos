package com.example.portfolio.exemplos.features.kotlin

val hello: (String) -> String = { name -> "Hello, $name" }
fun String.hello(): String = "Hello, $this"
val helloNew: String.() -> String = { "Hello, $this" }

data class NewTask(var title: String = "", var description: String = "")

enum class BoardColor {
    BLACK, WHITE, GREEN, BLUE
}

class Board {
    var title: String = ""
    var color: BoardColor = BoardColor.BLUE
    var tasks: MutableList<NewTask> = mutableListOf()

    fun task(init: NewTask.() -> Unit) {
        val task = NewTask()
        task.init()
        tasks.add(task)
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
            title = "Task 1"
            description = "Description Task 1"
        }
        task {
            title = "Task 2"
            description = "Description Task 2"
        }
        task {
            title = "Task 3"
            description = "Description Task 3"
        }
    }

    println("${board1.title}, ${board1.color} ")
}

fun Example_1_Main() {

    println(hello("Piotr"))
    println("Xicachu".hello())
    println("John".helloNew())

    val board1 = board {
        title = "Title 2"
        color = BoardColor.BLACK

        task {
            title = "Task 1"
            description = "Description Task 1"
        }
        task {
            title = "Task 2"
            description = "Description Task 2"
        }
        task {
            title = "Task 3"
            description = "Description Task 3"
        }
    }

    println("${board1.title}, ${board1.color} ")

    val taskOne = NewTask("Task 1", "Description Task 1")
    val taskTwo = NewTask("Task 2", "Description Task 2")
    val taskThree = NewTask("Task 3", "Description Task 3")

    val tasks = mutableListOf(taskOne, taskTwo, taskThree)

    val board = Board()
    board.title = "Title 1"
    board.color = BoardColor.WHITE
    board.tasks = tasks

}