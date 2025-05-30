package ai.bandroom.domain.model

import java.util.UUID
import java.time.Instant

data class User(
    val id: UUID,
    val email: String,
    val passwordHash: String,
    val role: String,
    val refreshToken: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
    val lastPasswordChange: Instant
)
