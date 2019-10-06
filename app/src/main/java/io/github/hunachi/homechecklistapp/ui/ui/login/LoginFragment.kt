package io.github.hunachi.homechecklistapp.ui.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import io.github.hunachi.homechecklistapp.R
import io.github.hunachi.homechecklistapp.ui.MyPreference
import io.github.hunachi.homechecklistapp.ui.data.User
import io.github.hunachi.homechecklistapp.ui.nonNullObserver
import io.github.hunachi.homechecklistapp.ui.toast
import org.koin.android.ext.android.inject

/**
 * Fragment for login.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val preference: MyPreference by inject()

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        loginButton = view.findViewById(R.id.btn_login)
        registerButton = view.findViewById(R.id.btn_register)

        view.findViewById<EditText>(R.id.name).addTextChangedListener {
            loginViewModel.changeName(it.toString())
        }
        view.findViewById<EditText>(R.id.password).addTextChangedListener {
            loginViewModel.changePassword(it.toString())
        }
        registerButton.setOnClickListener {
            loginViewModel.register()
        }
        loginButton.setOnClickListener {
            loginViewModel.login()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProviders.of(activity!!)[LoginViewModel::class.java]

        loginViewModel.buttonEnable.nonNullObserver(this) {
            changeButtonState(it)
        }
        loginViewModel.successRegister.nonNullObserver(this) {
            saveUserDataToDevice(it)
            startMainActivity()
        }
        loginViewModel.successLogin.nonNullObserver(this) {
            saveUserDataToDevice(it)
            startMainActivity()
        }
        loginViewModel.error.nonNullObserver(this) {
            activity?.toast("エラー:$it")
        }
        loginViewModel.spinner.nonNullObserver(this) {
            if (it) activity?.toast("ローディング中") else activity?.toast("終わり。")
        }
        loginViewModel.loginError.nonNullObserver(this) {
            activity?.toast("パスワードか名前を間違えています。")
        }
        loginViewModel.hadRegisteredError.nonNullObserver(this) {
            activity?.toast("すでに使われている名前です。")
        }
        loginViewModel.spinner.nonNullObserver(this){
            changeButtonState(!it)
        }
    }

    private fun changeButtonState(eneble: Boolean){
        registerButton.isEnabled = eneble
        loginButton.isEnabled = eneble
    }

    private fun saveUserDataToDevice(user: User) {
        preference.markUser(user)
    }

    private fun startMainActivity() {
        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
    }
}
