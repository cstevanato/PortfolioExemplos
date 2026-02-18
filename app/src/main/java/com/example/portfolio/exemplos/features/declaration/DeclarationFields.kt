package com.example.portfolio.exemplos.features.declaration

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class UserParameter(val name: String, val password: String)

class DeclarationFields(
    val id: Int,
    manager: String
) {
    var manager: String = manager
        private set

    val dogs: List<String>
        field : MutableList<String> = mutableListOf<String>()

    // TODO("Importante")
    val user: StateFlow<String>
        field = MutableStateFlow<String>("Teste")

    // StateFlow para manipular UserParameter
//    private val _userParameterStateFlow = MutableStateFlow(UserParameter(name = "", password = ""))
//    val userParameterStateFlow: StateFlow<UserParameter> = _userParameterStateFlow

    val userParameterStateFlow: StateFlow<UserParameter>
        field =  MutableStateFlow(UserParameter(name = "", password = ""))


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

    fun updateUserParameter(name: String, password: String) {
        userParameterStateFlow.value = UserParameter(name, password)
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