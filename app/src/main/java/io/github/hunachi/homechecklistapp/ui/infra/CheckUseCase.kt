package io.github.hunachi.homechecklistapp.ui.infra

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
* CheckListUseCaseからCheckUseCaseに名前を変更しました。
* */
class CheckUseCase {
    private val checkListRepository =
        CheckRepository()

    suspend fun checkList() = withContext(Dispatchers.IO) {
        return@withContext checkListRepository.checkList()
    }

    suspend fun check(id: String) = withContext(Dispatchers.IO){
        return@withContext checkListRepository.check(id)
    }
}