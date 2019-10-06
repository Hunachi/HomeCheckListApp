package io.github.hunachi.homechecklistapp.ui.ui.login

import android.util.Log
import androidx.lifecycle.*
import io.github.hunachi.homechecklistapp.ui.data.User
import io.github.hunachi.homechecklistapp.ui.infra.UserRepository
import io.github.hunachi.homechecklistapp.ui.infra.data.JoinGroupUseCase
import io.github.hunachi.homechecklistapp.ui.infra.data.RegisteredUserResult
import io.github.hunachi.homechecklistapp.ui.infra.data.UserLoginResult
import io.github.hunachi.homechecklistapp.ui.launchDataLoad
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = UserRepository()
    private val joinGroupUseCase = JoinGroupUseCase(repository)

    private val modifiableNameText:
            MutableLiveData<String> = MutableLiveData()
    private val nameText: LiveData<String> = modifiableNameText

    private val modifiablePasswordText:
            MutableLiveData<String> = MutableLiveData()
    private val passwordText: LiveData<String> = modifiablePasswordText

    private val modifiableSpinner: MutableLiveData<Boolean> = MutableLiveData()
    val spinner: LiveData<Boolean> = modifiableSpinner

    private val modifiableRegisterButtonEnable: MutableLiveData<Boolean> =
        MediatorLiveData<Boolean>().apply {
            addSource(nameText) {
                value = !nameText.value.isNullOrEmpty()
                        && !passwordText.value.isNullOrEmpty()
            }
            addSource(passwordText) {
                value = !nameText.value.isNullOrEmpty()
                        && !passwordText.value.isNullOrEmpty()
            }
        }
    val buttonEnable: LiveData<Boolean> = modifiableRegisterButtonEnable

    private val modifiableSuccessLogin: MutableLiveData<User> = MutableLiveData()
    val successLogin: LiveData<User> = modifiableSuccessLogin

    private val modifiableSuccessRegister: MutableLiveData<User> = MutableLiveData()
    val successRegister: LiveData<User> = modifiableSuccessRegister

    private val modifiableError: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = modifiableError

    private val modifiableLoginError: MutableLiveData<Boolean> = MutableLiveData()
    val loginError: LiveData<Boolean> = modifiableLoginError

    private val modifiableHadRegisteredError: MutableLiveData<Boolean> = MutableLiveData()
    val hadRegisteredError: LiveData<Boolean> = modifiableHadRegisteredError


    // ログインするための関数
    fun login() {
        try {
            val name = nameText.value ?: throw IllegalAccessError()
            val password = passwordText.value ?: throw IllegalAccessError()
            launchDataLoad(modifiableSpinner) {
                val hadUserRegisteredUser: RegisteredUserResult =
                    joinGroupUseCase.checkUserExist(name)
                if (hadUserRegisteredUser.isRegistered) {
                    val result: UserLoginResult =
                        joinGroupUseCase.login(hadUserRegisteredUser.user, password)
                    if (result.isAccept) {
                        modifiableSuccessLogin.postValue(result.user)
                    } else {
                        modifiableLoginError.postValue(true)
                    }
                } else {
                    modifiableLoginError.postValue(true)
                }
            }
        } catch (e: Exception) {
            e.fillInStackTrace()
            modifiableError.value = e
        }
    }

    // User登録するための関数
    fun register() {
        try {
            val name = nameText.value ?: throw IllegalAccessError()
            val password = passwordText.value ?: throw IllegalAccessError()
            viewModelScope.launch {

            }
            launchDataLoad(modifiableSpinner) {
                val result: RegisteredUserResult =
                    joinGroupUseCase.checkUserExist(name)
                if (result.isRegistered) {
                    Log.d("hoge", "hogehoge")
                    modifiableHadRegisteredError.postValue(true)
                } else {
                    Log.d("hoge", "hoge")
                    val user = joinGroupUseCase.register(name, password)
                    modifiableSuccessRegister.postValue(user)
                    Log.d("hoge", "hoge")
                }
            }
        } catch (e: Exception) {
            e.fillInStackTrace()
            modifiableError.value = e
        }
    }

    fun changeName(text: String?) {
        modifiableNameText.value = text
    }

    fun changePassword(text: String?) {
        modifiablePasswordText.value = text
    }
}