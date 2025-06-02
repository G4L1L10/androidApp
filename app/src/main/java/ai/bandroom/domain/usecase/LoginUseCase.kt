package ai.bandroom.domain.usecase

import ai.bandroom.data.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return repository.login(email, password)
    }
}
