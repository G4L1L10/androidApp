package ai.bandroom.viewmodel

import ai.bandroom.data.repository.AuthRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class SplashViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        checkToken()
    }

    private fun checkToken() {
        viewModelScope.launch {
            val token = authRepository.getAccessToken()
            Log.d("SplashVM", "📦 Retrieved token: $token")

            val isValid = authRepository.validateAccessToken()
            Log.d("SplashVM", "🔐 Token is valid: $isValid")

            _isLoggedIn.value = isValid
        }
    }
}
