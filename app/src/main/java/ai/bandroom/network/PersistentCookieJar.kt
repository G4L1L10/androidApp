package ai.bandroom.network

import android.content.Context
import android.webkit.CookieManager
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.net.HttpCookie
import java.util.concurrent.ConcurrentHashMap

class PersistentCookieJar(private val context: Context) : CookieJar {
    private val cookieStore = ConcurrentHashMap<String, List<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host] = cookies
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore[url.host] ?: emptyList()
    }
}
