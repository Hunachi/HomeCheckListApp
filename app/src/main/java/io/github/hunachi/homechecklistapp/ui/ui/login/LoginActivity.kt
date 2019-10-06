package io.github.hunachi.homechecklistapp.ui.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import io.github.hunachi.homechecklistapp.R

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val navController: NavController by lazy { findNavController(R.id.fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBarWithNavController(navController)
    }
}
