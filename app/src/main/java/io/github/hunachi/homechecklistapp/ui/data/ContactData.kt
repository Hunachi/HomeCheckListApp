package io.github.hunachi.homechecklistapp.ui.data

import java.text.SimpleDateFormat
import java.util.*

data class ContactData(
    var id: String = "",
    var checkItemId: String = "",
    var dateMili: Long = 0,
    var userName: String = "",
    var memo: String =""
) {

    fun stringDate(): String {
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
        return format.format(dateMili)
    }
}