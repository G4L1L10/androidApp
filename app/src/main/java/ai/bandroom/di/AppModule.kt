package ai.bandroom.di

import ai.bandroom.data.local.TokenManager
import ai.bandroom.data.local.dataStoreModule
import ai.bandroom.data.remote.AuthApiService
import ai.bandroom.data.repository.AuthRepository
import ai.bandroom.domain.usecase.LoginUseCase
import ai.bandroom.domain.usecase.SignupUseCase
import ai.bandroom.viewmodel.LoginViewModel
import ai.bandroom.viewmodel.SignupViewModel
import android.content.Context
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager

// ✅ Module for network configuration
val networkModule = module {

    // Provide OkHttpClient with Cookie support
    single {
        OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .build()
    }

    // Provide Retrofit instance
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/") // Localhost access from Android emulator
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provide AuthApiService using Retrofit
    single<AuthApiService> {
        get<Retrofit>().create(AuthApiService::class.java)
    }
}

// ✅ Repository module
val repositoryModule = module {
    // AuthRepository(authApiService, tokenManager)
    single { AuthRepository(get(), get()) }
}

// ✅ UseCases module
val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { SignupUseCase(get()) }
}

// ✅ ViewModel module
val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }
}

// ✅ Entry point: all modules combined
fun appModule(context: Context) = listOf(
    networkModule,
    dataStoreModule,    // Injects TokenManager using context
    repositoryModule,
    useCaseModule,
    viewModelModule
)
