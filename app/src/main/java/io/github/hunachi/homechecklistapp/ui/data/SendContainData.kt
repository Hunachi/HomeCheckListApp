package io.github.hunachi.homechecklistapp.ui.data

import java.util.*

data class SendContainData(
    val checkItemId: String = "",
    val dateMili: Long = Calendar.getInstance().timeInMillis,
    val userName: String,
    val memo: String
)