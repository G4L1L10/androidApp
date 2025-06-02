package ai.bandroom.utils

import android.util.Base64
import org.json.JSONObject

object JwtUtils {
    fun extractEmail(token: String): String? {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return null
            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_WRAP))
            val json = JSONObject(payload)
            json.getString("email") // ✅ Your backend should embed "email" in the payload
        } catch (e: Exception) {
            null
        }
    }
}
