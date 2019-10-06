package io.github.hunachi.homechecklistapp.ui.data

import android.icu.util.Calendar
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
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateMili
        val format = SimpleDateFormat("yyyy/mm/dd", Locale.JAPAN)
        return format.format(calendar)
    }
}