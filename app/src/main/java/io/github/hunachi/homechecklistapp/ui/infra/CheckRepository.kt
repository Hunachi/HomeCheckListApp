package io.github.hunachi.homechecklistapp.ui.infra

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.github.hunachi.homechecklistapp.ui.data.CheckItem
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/*
* CheckListRepositoryからCheckRepositoryに名前を変更しました。
* */
class CheckRepository {

    private val checkListCollection: CollectionReference by lazy {
        FirebaseFirestore.getInstance().collection(KEY_CHECKLIST)
    }

    suspend fun checkList(): List<CheckItem> = suspendCoroutine { crossinLine ->
        checkListCollection.orderBy(KEY_CONTENT)
            .get()
            .addOnSuccessListener { result ->
                val checkList: List<CheckItem> = if (result.documents.isNullOrEmpty()) listOf()
                else result.documents.map {
                    val context =
                        it.data?.filter { it.key == KEY_CONTENT }?.toList()?.first()?.second
                    CheckItem(it.id, context as String)
                }
                crossinLine.resume(checkList)
            }
            .addOnFailureListener { exception ->
                crossinLine.resumeWithException(exception)
            }
    }

    suspend fun check(id: String): CheckItem = suspendCoroutine { continuation ->
        checkListCollection
            .document(id)
            .get()
            .addOnSuccessListener{
                val context =
                    it.data?.filter { it.key == KEY_CONTENT }?.toList()?.first()?.second
                continuation.resume(CheckItem(it.id, context as String))
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    companion object {
        private const val KEY_CHECKLIST = "checkItem"
        private const val KEY_CONTENT = "content"
    }
}