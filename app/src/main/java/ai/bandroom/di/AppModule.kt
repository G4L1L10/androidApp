package ai.bandroom.di

import ai.bandroom.data.local.dataStoreModule
import ai.bandroom.data.local.TokenManager
import ai.bandroom.data.remote.AuthApiService
import ai.bandroom.data.repository.AuthRepository
import ai.bandroom.domain.usecase.LoginUseCase
import ai.bandroom.domain.usecase.SignupUseCase
import ai.bandroom.network.PersistentCookieJar
import ai.bandroom.viewmodel.LoginViewModel
import ai.bandroom.viewmodel.ProfileViewModel
import ai.bandroom.viewmodel.SignupViewModel
import ai.bandroom.viewmodel.SplashViewModel
import android.content.Context
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ✅ Network Module
val networkModule = module {

    // ✅ Provide PersistentCookieJar using context
    single { (context: Context) -> PersistentCookieJar(context) }

    // ✅ Provide OkHttpClient with cookie jar
    single {
        val context: Context = get()
        val cookieJar = get<PersistentCookieJar> { parametersOf(context) }

        OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .build()
    }

    // ✅ Provide Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ✅ Provide API service
    single<AuthApiService> {
        get<Retrofit>().create(AuthApiService::class.java)
    }
}

// ✅ Repository
val repositoryModule = module {
    single { AuthRepository(get(), get()) } // ✅ FIXED
}

// ✅ UseCases
val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { SignupUseCase(get()) }
}

// ✅ ViewModels
val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}

// ✅ Combined Modules
fun appModule(context: Context) = listOf(
    networkModule,
    dataStoreModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)
