package io.github.hunachi.homechecklistapp.ui.ui.createcontact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.hunachi.homechecklistapp.R

class CreateContactActivity : AppCompatActivity() {

    companion object {
        private const val KEY_ARG_USER = "user"

        fun createIntent(context: Context) = Intent(context, CreateContactActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)
        title = "連絡作成"
    }
}