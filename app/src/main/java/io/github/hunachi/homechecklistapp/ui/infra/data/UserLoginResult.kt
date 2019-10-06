package io.github.hunachi.homechecklistapp.ui.infra.data

import io.github.hunachi.homechecklistapp.ui.data.User

data class UserLoginResult(
    val isAccept: Boolean,
    val user: User
)
