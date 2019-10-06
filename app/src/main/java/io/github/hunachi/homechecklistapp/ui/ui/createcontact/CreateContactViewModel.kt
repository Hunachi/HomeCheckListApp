package io.github.hunachi.homechecklistapp.ui.ui.createcontact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.hunachi.homechecklistapp.ui.data.*
import io.github.hunachi.homechecklistapp.ui.infra.CheckListUseCase
import io.github.hunachi.homechecklistapp.ui.infra.ContactUseCase
import io.github.hunachi.homechecklistapp.ui.launchDataLoad
import kotlinx.coroutines.launch

class CreateContactViewModel : ViewModel() {

    private val checkListUseCase = CheckListUseCase()
    private val contactUseCase = ContactUseCase()

    private val modifiableCheckList:
            MutableLiveData<List<CheckListItem>> = MutableLiveData()
    val checkList: LiveData<List<CheckListItem>> = modifiableCheckList

    private val modifiableChooseItem:
            MutableLiveData<CheckItem> = MutableLiveData()
    val chooseItem: LiveData<CheckItem> = modifiableChooseItem

    private val modifiableSuccessSend:
            MutableLiveData<ContactData> = MutableLiveData()
    val successSend: LiveData<ContactData> = modifiableSuccessSend

    private val modifiableError:
            MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = modifiableError

    private val modifiableSpinner: MutableLiveData<Boolean> = MutableLiveData()
    val spinner: LiveData<Boolean> = modifiableSpinner

    // 前選ばれていたItemと選ばれたItemの更新。
    fun changeChooseItem(checkItem: CheckItem) {
        val previousItem = chooseItem.value
        modifiableCheckList.value = checkList.value?.map {
            when {
                it.checkItem == checkItem -> {
                    CheckListItem(it.checkItem, true)
                }
                it.checkItem == previousItem -> {
                    CheckListItem(it.checkItem, false)
                }
                else -> it
            }
        }
        // ここで更新しないと前選ばれていたItemがわからなくなる。
        modifiableChooseItem.value = checkItem
    }

    // Listの更新
    fun refreshList() {
        try {
            viewModelScope.launch {
                // Firestoreからデータを取得。
                val checkList = checkListUseCase.checkList()
                modifiableCheckList.value = checkList.map {
                    CheckListItem(it, false)
                }
            }
        } catch (e: Exception) {
            modifiableError.value = e
        }
    }

    fun sendContact(user: User, memo: String){
        val contactData = SendContainData(
            checkItemId = chooseItem.value?.id ?: return,
            userName = user.name,
            memo = memo
        )
        try {
            launchDataLoad(modifiableSpinner){
                val contactData = contactUseCase.sendContact(contactData)
                modifiableSuccessSend.value = contactData
            }
        }catch (e: Exception){
            modifiableError.value = e
        }
    }
}
