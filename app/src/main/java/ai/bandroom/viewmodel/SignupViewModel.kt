package ai.bandroom.viewmodel

import ai.bandroom.domain.usecase.SignupUseCase
import ai.bandroom.viewmodel.state.AuthUiEvent
import ai.bandroom.ui.screens.signup.SignupUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val signupUseCase: SignupUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.EmailChanged -> _uiState.value = _uiState.value.copy(email = event.email)
            is AuthUiEvent.PasswordChanged -> _uiState.value = _uiState.value.copy(password = event.password)
            AuthUiEvent.Submit -> signup()
        }
    }

    private fun signup() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = signupUseCase(_uiState.value.email, _uiState.value.password)
            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(isSuccess = true, isLoading = false)
            } else {
                _uiState.value.copy(error = result.exceptionOrNull()?.message, isLoading = false)
            }
        }
    }
}
