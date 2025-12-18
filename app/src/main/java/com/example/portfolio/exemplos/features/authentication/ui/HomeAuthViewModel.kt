package com.example.portfolio.exemplos.features.authentication.ui

import androidx.lifecycle.ViewModel
import com.example.portfolio.exemplos.features.authentication.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


data class HomeAuthState(
    val userEmail: String? = null,
    val userId: String? = null,
    val isLoggedOut: Boolean = false
)

@HiltViewModel
class HomeAuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

        private val _state = MutableStateFlow(HomeAuthState())
    val state: StateFlow<HomeAuthState> = _state.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val currentUser = authRepository.currentUser
        _state.update {
            it.copy(
                userEmail = currentUser?.email,
                userId = currentUser?.uid,
                isLoggedOut = false
            )
        }
    }

    fun signOut() {
        authRepository.signOut()
        _state.update { it.copy(isLoggedOut = true) }
    }
}