package io.github.hunachi.homechecklistapp.ui.infra

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserUseCase {
    private val userRepository = UserRepository()

    suspend fun users() = withContext(Dispatchers.IO) {
        return@withContext userRepository.users()
    }

    suspend fun user(id:String) = withContext(Dispatchers.IO){
        return@withContext userRepository.user(id)
    }
}