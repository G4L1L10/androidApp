package ai.bandroom.viewmodel

import ai.bandroom.domain.usecase.LoginUseCase
import ai.bandroom.viewmodel.state.AuthUiEvent
import ai.bandroom.ui.screens.login.LoginUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.EmailChanged -> _uiState.value = _uiState.value.copy(email = event.email)
            is AuthUiEvent.PasswordChanged -> _uiState.value = _uiState.value.copy(password = event.password)
            AuthUiEvent.Submit -> login()
        }
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = loginUseCase(_uiState.value.email, _uiState.value.password)
            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(isSuccess = true, isLoading = false)
            } else {
                _uiState.value.copy(error = result.exceptionOrNull()?.message, isLoading = false)
            }
        }
    }
}
