package io.github.hunachi.homechecklistapp.ui.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.hunachi.homechecklistapp.ui.data.ContactItem
import io.github.hunachi.homechecklistapp.ui.infra.CheckUseCase
import io.github.hunachi.homechecklistapp.ui.infra.ContactUseCase
import io.github.hunachi.homechecklistapp.ui.launchDataLoad

class ContactViewModel : ViewModel() {

    private val contactUseCase = ContactUseCase()

    private val checkUseCase = CheckUseCase()

    private val modifiableContactList:
            MutableLiveData<List<ContactItem>> = MutableLiveData()
    val contactList: LiveData<List<ContactItem>> = modifiableContactList

    private val modifiableError:
            MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = modifiableError

    private val modifiableSpinner: MutableLiveData<Boolean> = MutableLiveData()
    val spinner: LiveData<Boolean> = modifiableSpinner

    fun refreshContactList() {
        try {
            launchDataLoad(modifiableSpinner) {
                val contactDataList = contactUseCase.todayContact()
                modifiableContactList.value = contactDataList.map {
                    val checkItemName = checkUseCase.check(it.checkItemId).content
                    ContactItem(it, checkItemName)
                }
            }
        } catch (e: Exception) {
            modifiableError.value = e
        }
    }
}