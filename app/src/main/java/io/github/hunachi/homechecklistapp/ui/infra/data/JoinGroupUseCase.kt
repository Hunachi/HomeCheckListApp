package io.github.hunachi.homechecklistapp.ui.infra.data

import io.github.hunachi.homechecklistapp.ui.data.User
import io.github.hunachi.homechecklistapp.ui.infra.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JoinGroupUseCase(private val userRepository: UserRepository) {

    suspend fun checkUserExist(name: String) = withContext(Dispatchers.IO) {
        return@withContext userRepository.hasUserRegistered(name)
    }

    suspend fun register(name: String, password: String): User = withContext(Dispatchers.IO) {
        return@withContext userRepository.registerUser(name, password)
    }

    suspend fun login(user: User, password: String): UserLoginResult = withContext(Dispatchers.IO) {
        return@withContext userRepository.login(user, password)
    }
}