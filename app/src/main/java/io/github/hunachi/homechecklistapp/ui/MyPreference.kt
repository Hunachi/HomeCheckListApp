package io.github.hunachi.homechecklistapp.ui

import android.content.Context
import androidx.core.content.edit
import io.github.hunachi.homechecklistapp.ui.data.User

class MyPreference(context: Context) {

    private val preference = context.getSharedPreferences(KEY_APPLICATION, Context.MODE_PRIVATE)

    fun markUserId(id: String) = preference.edit { putString(KEY_USER_ID, id) }

    fun id() = preference.getString(KEY_USER_ID, "") ?: ""

    fun markUserName(name: String) = preference.edit {
        putString(KEY_UESR_NAME, name)
    }

    fun name() = preference.getString(KEY_UESR_NAME, "") ?: ""

    fun markUser(user: User){
        markUserId(user.id)
        markUserName(user.name)
    }

    fun user() = User(id(), name())

    companion object {
        private const val KEY_APPLICATION = "home-check-list-app"
        private const val KEY_USER_ID = "id"
        private const val KEY_UESR_NAME = "name"
    }
}