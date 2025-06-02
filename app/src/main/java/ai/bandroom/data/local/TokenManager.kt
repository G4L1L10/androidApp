package ai.bandroom.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenManager(private val context: Context) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private const val TAG = "TokenManager"
    }

    suspend fun saveAccessToken(token: String) {
        Log.d(TAG, "💾 Saving access token")
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            val token = prefs[ACCESS_TOKEN_KEY]
            Log.d(TAG, "📥 Fetched token from DataStore: ${token?.take(10)}...")
            token
        }
    }

    suspend fun clearTokens() {
        Log.d(TAG, "🧹 Clearing all tokens from DataStore")
        context.dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }
}
