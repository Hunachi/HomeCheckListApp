package io.github.hunachi.homechecklistapp.ui.infra.data

import io.github.hunachi.homechecklistapp.ui.data.User

data class RegisteredUserResult(
    val isRegistered: Boolean,
    val user: User
)