package io.github.hunachi.homechecklistapp.ui.infra

import android.icu.util.Calendar
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.github.hunachi.homechecklistapp.ui.data.ContactData
import io.github.hunachi.homechecklistapp.ui.data.SendContainData
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ContactRepository {

    private val contactCollection: CollectionReference by lazy {
        FirebaseFirestore.getInstance().collection(KEY_CONTACT)
    }

    suspend fun createContact(contactData: SendContainData, date: Long) =
        suspendCoroutine<ContactData> { continuation ->
            val data = hashMapOf(
                KEY_CHECK_ID to contactData.checkItemId,
                KEY_DATE to date,
                KEY_MEMO to contactData.memo,
                KEY_USER_NAME to contactData.userName
            )
            contactCollection.add(data)
                .addOnSuccessListener {
                    continuation.resume(ContactData().apply {
                        id = it.id
                        checkItemId = contactData.checkItemId
                        dateMili = contactData.dateMili
                        memo = contactData.memo
                        userName = contactData.userName
                    })
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    suspend fun contacts() = suspendCoroutine<List<ContactData>> { crossinLine ->
        contactCollection.get()
            .addOnSuccessListener { result ->
                val checkList: List<ContactData> = if (result.documents.isNullOrEmpty()) listOf()
                else result.documents.map {
                    ContactData().apply {
                        id = it.id
                        it.data?.forEach {
                            when (it.key) {
                                KEY_CHECK_ID -> checkItemId = it.value.toString()
                                KEY_DATE -> dateMili = it.value as Long
                                KEY_USER_NAME -> userName = it.value.toString()
                                KEY_MEMO -> memo = it.value.toString()
                            }
                        }
                    }
                }
                crossinLine.resume(checkList)
            }
            .addOnFailureListener { exception ->
                crossinLine.resumeWithException(exception)
            }
    }

    suspend fun todayContact() = coroutineScope {
        // 前日の6時以降〜
        val todayStart = Calendar.getInstance()
        todayStart.set(Calendar.DAY_OF_YEAR, todayStart.get(Calendar.DAY_OF_YEAR) - 1)
        todayStart.set(Calendar.HOUR_OF_DAY, 6)
        // 当日の21時までの登録された予定（その日使う予定のみ）を持ってくる。
        val todayEnd = Calendar.getInstance()
        todayEnd.set(Calendar.HOUR_OF_DAY, 21)
        contacts()
            .filter {
                it.dateMili >= todayStart.timeInMillis && it.dateMili <= todayEnd.timeInMillis
            }
    }

    companion object {
        private const val KEY_CONTACT = "contact"
        private const val KEY_CHECK_ID = "check-id"
        private const val KEY_DATE = "date"
        private const val KEY_MEMO = "memo"
        private const val KEY_USER_NAME = "user-name"
    }
}