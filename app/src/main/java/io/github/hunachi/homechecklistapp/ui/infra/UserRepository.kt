package io.github.hunachi.homechecklistapp.ui.infra

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.github.hunachi.homechecklistapp.ui.data.User
import io.github.hunachi.homechecklistapp.ui.infra.data.RegisteredUserResult
import io.github.hunachi.homechecklistapp.ui.infra.data.UserLoginResult
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserRepository {

    private val userCollection: CollectionReference by lazy {
        FirebaseFirestore.getInstance().collection(KEY_USER)
    }

    suspend fun hasUserRegistered(name: String) =
        suspendCoroutine<RegisteredUserResult> { continuation ->
            // userコレクションのkeyが”name”の時の値がnameなデータのみ取得。
            // document()とすると、全てのドキュメントが返ってくる。
            userCollection.whereEqualTo(KEY_NAME, name)
                .get()
                .addOnSuccessListener { snapshot ->
                    // 成功したらこの中身が実行される。
                    // suspendCoroutineから抜け出す。
                    val result = snapshot.convertCheckResult(name)
                    continuation.resume(result)
                }
                .addOnFailureListener { exception ->
                    // suspendCoroutineからErrorとして抜け出す。
                    continuation.resumeWithException(exception)
                }
        }

    private fun QuerySnapshot.convertCheckResult(name: String) =
        if (documents.isEmpty()) {
            RegisteredUserResult(false, User())
        } else {
            // 一致するものがあった＝登録済み。
            val userId = documents.first()?.id ?: ""
            RegisteredUserResult(
                isRegistered = documents.isNotEmpty(),
                user = User(userId, name)
            )
        }

    suspend fun registerUser(name: String, password: String) =
        suspendCoroutine<User> { continuation ->
            // パスワードを平文保存はまずいのでHash化。
            val hashPassword = password.hashCode()
            // 保存するドキュメントを作る
            Log.d("hoge1", hashPassword.toString())
            val user = hashMapOf(
                KEY_NAME to name,
                KEY_PASSWORD to hashPassword
            )
            // ドキュメントの追加。
            userCollection.add(user)
                .addOnSuccessListener {
                    Log.d("hoge2", it.toString())
                    continuation.resume(User(it.id, name))
                }
                .addOnFailureListener {
                    Log.d("hoge3", it.toString())
                    continuation.resumeWithException(it)
                }
        }// -874940727
            // -874940727

    suspend fun login(user: User, password: String): UserLoginResult =
        suspendCoroutine { continuation ->
            val hashPassword = password.hashCode()
            Log.d("hoge1", hashPassword.toString())
            /// IDが今回調べたいuserのidと一致する1つのドキュメントを取得。
            userCollection.document(user.id)
                .get()
                .addOnSuccessListener { snapshot ->
                    // Userの名前とHashにかけたパスワードが一致したらそれぞれtrueになる。
                    val isSameName = snapshot.data?.filter {
                        it.key == KEY_NAME && it.value == user.name
                    }?.isNotEmpty() ?: false
                    val isSamePassword = snapshot.data?.filter {
                        it.key == KEY_PASSWORD && it.value == hashPassword.toString()
                    }?.isNotEmpty() ?: false
                    continuation.resume(
                        UserLoginResult(
                            isSameName && isSamePassword,
                            User(user.id, user.name)
                        )
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    suspend fun users() = suspendCoroutine<List<User>> { continuation ->
        userCollection
            .get()
            .addOnSuccessListener { snapshot ->
                continuation.resume(snapshot.documents.map {
                    User(it.id, it.data?.filter { it.key == KEY_NAME }?.values?.first().toString())
                })
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    suspend fun user(id: String) = suspendCoroutine<User>{continuation ->
        userCollection.document(id)
            .get()
            .addOnSuccessListener {
                it.data?.forEach {
                    when(it.key){
                        KEY_NAME -> continuation.resume(User(id, name = it.value.toString()))
                    }
                }
                continuation.resumeWithException(NullPointerException())
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    companion object {
        private const val KEY_USER = "user"
        private const val KEY_NAME = "name"
        private const val KEY_PASSWORD = "password"
    }
}