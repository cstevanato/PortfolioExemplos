package com.example.portfolio.exemplos.features.declaration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeclarationFields(
    val id: Int,
    manager: String
) {
    var manager: String = manager
        private set

    val dogs: List<String>
        field : MutableList<String> = mutableListOf<String>()

    val user: StateFlow<String>
        field = MutableStateFlow<String>("Teste")


    fun onManagerChange(manager: String) {
        println("$manager is taking over manager duties from ${this.manager}")
        this.manager = manager
    }

    fun onDogDropOff(dog: String) {
        dogs.remove(dog)
    }

    fun onDogAddOff(dog: String) {
        dogs.add(dog)
    }

}

fun main() {
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    val declarationFields = DeclarationFields(1, "John")
    declarationFields.onManagerChange("Jane")
    declarationFields.onDogDropOff("Fido")

    scope.launch {
        declarationFields.user.collect {
            println(it)
        }
    }
}