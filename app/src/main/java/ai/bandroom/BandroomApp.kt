package ai.bandroom

import android.app.Application
import ai.bandroom.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BandroomApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BandroomApp)
            modules(appModule(this@BandroomApp))
        }
    }
}
