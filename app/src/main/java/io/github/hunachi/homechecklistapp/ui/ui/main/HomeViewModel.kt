package io.github.hunachi.homechecklistapp.ui.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.hunachi.homechecklistapp.ui.data.CheckItem
import io.github.hunachi.homechecklistapp.ui.data.CheckListItem
import io.github.hunachi.homechecklistapp.ui.data.User

class HomeViewModel: ViewModel() {

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
}