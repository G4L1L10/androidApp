package ai.bandroom.viewmodel

import ai.bandroom.data.repository.AuthRepository
import ai.bandroom.utils.JwtUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _logoutSuccess = MutableStateFlow<Boolean?>(null)
    val logoutSuccess = _logoutSuccess.asStateFlow()

    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> = _email.asStateFlow()

    init {
        // Fetch the email from the token on init
        viewModelScope.launch {
            val token = repository.getAccessToken()
            val extractedEmail = token?.let { JwtUtils.extractEmail(it) }
            println("📩 Extracted email: $extractedEmail")
            _email.value = extractedEmail
        }
    }

    fun logout() {
        viewModelScope.launch {
            val success = repository.logout()
            println("🔌 Logout triggered: $success")
            _logoutSuccess.value = success
        }
    }
}
