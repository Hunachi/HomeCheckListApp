package io.github.hunachi.homechecklistapp.ui.infra

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.github.hunachi.homechecklistapp.ui.data.ContactData
import io.github.hunachi.homechecklistapp.ui.data.SendContainData
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

    companion object {
        private const val KEY_CONTACT = "contact"
        private const val KEY_CHECK_ID = "check-id"
        private const val KEY_DATE = "date"
        private const val KEY_MEMO = "memo"
        private const val KEY_USER_NAME = "user-name"
    }
}