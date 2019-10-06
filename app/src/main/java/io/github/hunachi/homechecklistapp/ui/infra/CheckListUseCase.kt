package io.github.hunachi.homechecklistapp.ui.infra

import io.github.hunachi.homechecklistapp.ui.infra.CheckListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckListUseCase {
    private val checkListRepository =
        CheckListRepository()

    suspend fun checkList() = withContext(Dispatchers.IO) {
        return@withContext checkListRepository.checkList()
    }
}