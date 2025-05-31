package ai.bandroom.di

import ai.bandroom.data.local.TokenManager
import ai.bandroom.data.local.dataStoreModule
import ai.bandroom.data.remote.AuthApiService
import ai.bandroom.data.repository.AuthRepository
import ai.bandroom.domain.usecase.LoginUseCase
import ai.bandroom.domain.usecase.SignupUseCase
import ai.bandroom.viewmodel.LoginViewModel
import ai.bandroom.viewmodel.SignupViewModel
import ai.bandroom.viewmodel.ProfileViewModel
import android.content.Context
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy

// ✅ Module for network configuration
val networkModule = module {

    // ✅ Use a CookieManager that allows all cookies (including HttpOnly, refresh_token)
    single {
        val cookieManager = CookieManager().apply {
            setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        }

        OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(cookieManager))
            .build()
    }

    // ✅ Provide Retrofit instance
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/") // Emulator → localhost
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ✅ Provide API service interface
    single<AuthApiService> {
        get<Retrofit>().create(AuthApiService::class.java)
    }
}

// ✅ Repository module
val repositoryModule = module {
    single { AuthRepository(get(), get()) }
}

// ✅ UseCases module
val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { SignupUseCase(get()) }
}

// ✅ ViewModels
val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}

// ✅ Combine all modules
fun appModule(context: Context) = listOf(
    networkModule,
    dataStoreModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)
