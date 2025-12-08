package com.example.portfolio.exemplos

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//sealed interface NavEvent {
//    data class NavigateTo(val dest: Dest) : NavEvent
//    data class PopTo(val dest: Dest) : NavEvent
//    data class ReplaceTop(val dest: Dest) : NavEvent
//    data object Pop : NavEvent
//    data class ResetTo(val dest: Dest) : NavEvent
//}
//

@HiltViewModel
class BasicNavigationViewModel @Inject constructor() : ViewModel() {
    val backStack = mutableStateListOf<Dest>(Dest.Home)


//    // Event bus for navigation commands
//    private val _events = MutableSharedFlow<NavEvent>(extraBufferCapacity = 64)
//    val events = _events.asSharedFlow()
//
//    init {
//        // Consume events and mutate backStack accordingly
//        viewModelScope.launch {
//            events.collect { event ->
//                when (event) {
//                    is NavEvent.NavigateTo -> backStack.add(event.dest)
//                    is NavEvent.Pop -> backStack.removeLastOrNull()
//                    is NavEvent.PopTo -> {
//                        // Pop until the specified destination is on top (if present)
//                        while (backStack.isNotEmpty() && backStack.last() != event.dest) {
//                            backStack.removeLast()
//                        }
//                    }
//
//                    is NavEvent.ReplaceTop -> {
//                        backStack.removeLastOrNull()
//                        backStack.add(event.dest)
//                    }
//
//                    is NavEvent.ResetTo -> {
//                        backStack.clear()
//                        backStack.add(event.dest)
//                    }
//                }
//            }
//        }
//    }
//
//
//    // Convenience dispatchers (UI calls these; ViewModel handles mutations)
//    fun navigateTo(dest: Dest) {
//        _events.tryEmit(NavEvent.NavigateTo(dest))
//    }
//
//    fun pop() {
//        _events.tryEmit(NavEvent.Pop)
//    }
//
//    fun popTo(dest: Dest) {
//        _events.tryEmit(NavEvent.PopTo(dest))
//    }
//
//    fun replaceTop(dest: Dest) {
//        _events.tryEmit(NavEvent.ReplaceTop(dest))
//    }
//
//    fun resetTo(dest: Dest) {
//        _events.tryEmit(NavEvent.ResetTo(dest))
//    }
}