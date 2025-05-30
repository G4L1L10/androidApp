package ai.bandroom.domain.usecase

import ai.bandroom.data.repository.AuthRepository

class SignupUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.register(email, password)
    }
}
