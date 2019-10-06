package io.github.hunachi.homechecklistapp.ui

import android.app.Application
import io.github.hunachi.homechecklistapp.ui.infra.CheckListRepository
import io.github.hunachi.homechecklistapp.ui.infra.CheckListUseCase
import io.github.hunachi.homechecklistapp.ui.infra.UserRepository
import io.github.hunachi.homechecklistapp.ui.infra.data.JoinGroupUseCase
import io.github.hunachi.homechecklistapp.ui.ui.createcontact.CreateContactViewModel
import io.github.hunachi.homechecklistapp.ui.ui.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)

            modules(mainModule)
        }
    }

    private val mainModule = module {
        single { UserRepository() }

        single { JoinGroupUseCase(get()) }

        single { CheckListRepository() }

        single { CheckListUseCase() }

        factory { MyPreference(get()) }

        viewModel { LoginViewModel() }

        viewModel { CreateContactViewModel() }
    }
}