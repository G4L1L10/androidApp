package ai.bandroom.data.remote

import ai.bandroom.data.remote.models.*
import retrofit2.Response
import retrofit2.http.*

interface AuthApiService {

    @POST("/auth/register")
    suspend fun register(@Body request: SignupRequest): Response<AuthResponse>

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("/auth/refresh")
    suspend fun refresh(): Response<AuthResponse>

    @POST("/auth/logout")
    suspend fun logout(): Response<Void>

    @GET("/auth/validate")
    suspend fun validateToken(@Header("Authorization") token: String): Response<Map<String, Any>>
}
