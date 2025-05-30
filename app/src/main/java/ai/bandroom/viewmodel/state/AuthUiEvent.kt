package ai.bandroom.viewmodel.state

sealed class AuthUiEvent {
    data class EmailChanged(val email: String) : AuthUiEvent()
    data class PasswordChanged(val password: String) : AuthUiEvent()
    object Submit : AuthUiEvent()
}
