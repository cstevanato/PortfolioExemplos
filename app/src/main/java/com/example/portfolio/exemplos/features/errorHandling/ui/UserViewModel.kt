package com.example.portfolio.exemplos.features.errorHandling.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.exemplos.features.errorHandling.data.AuthRepository
import com.example.portfolio.exemplos.features.errorHandling.domain.PasswordError
import com.example.portfolio.exemplos.features.errorHandling.domain.Result
import com.example.portfolio.exemplos.features.errorHandling.domain.UserDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// https://www.youtube.com/watch?v=MiLN2vs2Oe0

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val eventChannel = Channel<UserEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onRegisterClick(password: String) {
        when (val result = userDataValidator.validatePassword(password)) {
            is Result.Success -> {

            }

            is Result.Error -> {
                when (result.error) {
                    PasswordError.NO_DIGIT -> {}
                    PasswordError.NO_UPPERCASE -> {}
                    PasswordError.NO_SPECIAL_CHAR -> {}
                    PasswordError.TOO_SHORT -> {}
                }
            }
        }

        viewModelScope.launch {
            when (val result = authRepository.register(password)) {
                is Result.Success -> {
                    val user = result.data
                }

                is Result.Error -> {
                    val errorMessage = result.error.asUiText()
                    eventChannel.send(UserEvent.Error(errorMessage))
//                    when (result.error) {
//                        DataError.Network.REQUEST_TIMEOUT -> TODO()
//                        DataError.Network.UNAUTHORIZED -> TODO()
//                        DataError.Network.SERVER_ERROR -> TODO()
//                        DataError.Network.UNKNOWN -> TODO()
//                        DataError.Network.TOO_MANY_REQUESTS -> TODO()
//                        DataError.Network.NO_INTERNET_CONNECTION -> TODO()
//                        DataError.Network.PAYLOAD_TOO_LARGE -> TODO()
//                        DataError.Network.SERIALIZATION -> TODO()
//                        DataError.Network.DESERIALIZATION -> TODO()
//                    }
                }
            }
        }

    }
}

sealed interface UserEvent {
    data class Error(val error: UiText) : UserEvent
}
