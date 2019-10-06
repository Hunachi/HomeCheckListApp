package io.github.hunachi.homechecklistapp.ui.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.hunachi.homechecklistapp.R
import io.github.hunachi.homechecklistapp.ui.ui.createcontact.CreateContactActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navController: NavController by lazy { findNavController(R.id.fragment_nav_host) }

    private val argument: MainActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // UserをArgumentとして渡すのもあり。
        // ただし、普通はこのActivityから

        // DataBindingを使うとこれはいらなくなる。
        val navigationView: BottomNavigationView = findViewById(R.id.navigation_view)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener {
            startActivity(CreateContactActivity.createIntent(this))
        }
        // buttom app barにあるNavigation Viewと紐づける。
        navigationView.setupWithNavController(navController)

        // Fragment切り替え時にTitleのみを変更する。
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.contactFragment,
                R.id.homeFragment
            ),
            null
        )
        // Titleを自動で切り替えてくれるようにする。
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
