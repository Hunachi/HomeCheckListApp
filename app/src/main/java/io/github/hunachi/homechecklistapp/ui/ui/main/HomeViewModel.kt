package io.github.hunachi.homechecklistapp.ui.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.hunachi.homechecklistapp.ui.data.CheckItem
import io.github.hunachi.homechecklistapp.ui.data.CheckListItem
import io.github.hunachi.homechecklistapp.ui.data.User
import io.github.hunachi.homechecklistapp.ui.infra.CheckListUseCase
import io.github.hunachi.homechecklistapp.ui.infra.UserUseCase
import io.github.hunachi.homechecklistapp.ui.launchDataLoad

class HomeViewModel: ViewModel() {

    private val userUseCase = UserUseCase()
    private val checkListUseCase = CheckListUseCase()

    private val modifiableCheckList:
            MutableLiveData<List<CheckListItem>> = MutableLiveData()
    val checkList: LiveData<List<CheckListItem>> = modifiableCheckList

    private val modifiableChooseItem:
            MutableLiveData<CheckItem> = MutableLiveData()
    val chooseItem: LiveData<CheckItem> = modifiableChooseItem

    private val modifiableUserList:
            MutableLiveData<List<User>> = MutableLiveData()
    val userList: LiveData<List<User>> = modifiableUserList

    private val modifiableError:
            MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = modifiableError

    private val modifiableSpinner: MutableLiveData<Boolean> = MutableLiveData()
    val spinner: LiveData<Boolean> = modifiableSpinner

    fun updateChecklist(){
        try {
            launchDataLoad(modifiableSpinner){
                val list = checkListUseCase.checkList()
                modifiableCheckList.value = list.map {
                    CheckListItem(checkItem = it)
                }
            }
        }catch (e: Exception){
            modifiableError.value = e
        }
    }

    fun updateUsersList(){
        try {
            launchDataLoad(modifiableSpinner){
                val list = userUseCase.users()
                modifiableUserList.value = list
            }
        }catch (e: Exception){
            modifiableError.value = e
        }
    }
}