package ai.bandroom.data.local

import android.content.Context
import org.koin.dsl.module

val dataStoreModule = module {
    single { provideTokenManager(get()) }
}

fun provideTokenManager(context: Context): TokenManager {
    return TokenManager(context)
}
