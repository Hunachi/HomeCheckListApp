package io.github.hunachi.homechecklistapp.ui.infra

import android.icu.util.Calendar
import io.github.hunachi.homechecklistapp.ui.data.SendContainData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactUseCase {
    private val contactRepository = ContactRepository()

    suspend fun sendContact(contactData: SendContainData) = withContext(Dispatchers.IO) {
        return@withContext contactRepository.createContact(contactData,defaultData())
    }

    suspend fun contacts() = withContext(Dispatchers.IO) {
        return@withContext contactRepository.contacts()
    }

    suspend fun todayContact() = withContext(Dispatchers.IO) {
        return@withContext contactRepository.todayContact()
    }

    private fun defaultData(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1)
        return calendar.timeInMillis
    }
}