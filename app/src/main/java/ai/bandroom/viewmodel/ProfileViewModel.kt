package ai.bandroom.viewmodel

import ai.bandroom.data.repository.AuthRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _logoutSuccess = MutableStateFlow<Boolean?>(null)
    val logoutSuccess = _logoutSuccess.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            val success = repository.logout()
            println("🔌 Logout triggered: $success") // ✅ Add this
            _logoutSuccess.value = success
        }
    }
}
