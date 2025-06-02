package ai.bandroom.data.repository

import ai.bandroom.data.local.TokenManager
import ai.bandroom.data.remote.AuthApiService
import ai.bandroom.data.remote.models.*
import android.util.Log
import kotlinx.coroutines.flow.first

class AuthRepository(
    private val api: AuthApiService,
    private val tokenManager: TokenManager
) {

    suspend fun register(email: String, password: String): Result<Unit> {
        return try {
            val response = api.register(SignupRequest(email, password))
            if (response.isSuccessful) {
                val token = response.body()?.access_token ?: ""
                Log.d("AuthRepo", "✅ Registered. Saving token: $token")
                tokenManager.saveAccessToken(token)
                Result.success(Unit)
            } else {
                Log.e("AuthRepo", "Register failed: ${response.code()}")
                Result.failure(Exception(response.errorBody()?.string() ?: "Registration failed"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Network error during registration", e)
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                val token = response.body()?.access_token
                return if (!token.isNullOrEmpty()) {
                    Log.d("AuthRepo", "✅ Login success, token: $token")
                    tokenManager.saveAccessToken(token)
                    Log.d("AuthRepo", "💾 Token saved to DataStore")
                    Result.success(token)
                } else {
                    Result.failure(Exception("Access token missing"))
                }
            } else {
                Log.e("AuthRepo", "Login failed: ${response.code()}")
                Result.failure(Exception(response.errorBody()?.string() ?: "Login failed"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Network error during login", e)
            Result.failure(e)
        }
    }

    suspend fun validateAccessToken(): Boolean {
        return try {
            val token = tokenManager.getAccessToken().first() ?: return false
            Log.d("AuthRepo", "🔐 Validating token: $token")
            val response = api.validateToken("Bearer $token")
            Log.d("AuthRepo", "🧾 Validation response: ${response.code()}")
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("AuthRepo", "Token validation failed", e)
            false
        }
    }

    suspend fun refreshAccessToken(): Boolean {
        return try {
            val response = api.refresh()
            if (response.isSuccessful) {
                val newToken = response.body()?.access_token ?: return false
                tokenManager.saveAccessToken(newToken)
                Log.d("AuthRepo", "🔄 Refreshed and saved token: $newToken")
                return true
            } else {
                Log.e("AuthRepo", "Refresh token failed: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Network error during refresh", e)
            false
        }
    }

    suspend fun logout(): Boolean {
        return try {
            Log.d("AuthRepo", "📨 logout() - calling backend")
            val response = api.logout()
            Log.d("AuthRepo", "📬 logout() response code: ${response.code()}")
            Log.d("AuthRepo", "🧹 logout() - clearing tokens")
            tokenManager.clearTokens()
            val success = response.isSuccessful
            Log.d("AuthRepo", "✅ logout() - API success: $success")
            success
        } catch (e: Exception) {
            Log.e("AuthRepo", "❌ Logout failed", e)
            false
        }
    }

    suspend fun getAccessToken(): String? {
        return tokenManager.getAccessToken().first()
    }
}
