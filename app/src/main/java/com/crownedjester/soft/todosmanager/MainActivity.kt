package com.crownedjester.soft.todosmanager

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController
    }

    private var countBackPressed = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            supportActionBar?.title =
                if (destination.id == R.id.dashboardFragment) {
                    "Todos List"
                } else {
                    "Add / Edit Todo"
                }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home)
            navController.navigateIfAvailable()
        else super.onOptionsItemSelected(
            item
        )

    }

    override fun onBackPressed() {

        if (!navController.navigateIfAvailable()) {

            countBackPressed += 1

            lifecycleScope.launch {
                delay(1250)
                countBackPressed = 0
            }

            if (countBackPressed == 1) {
                Toast.makeText(this, "Tap backpress again to exit", Toast.LENGTH_LONG).show()
            }

            if (countBackPressed == 2) {
                finishActivity(0)
                exitProcess(0)
            }

        }

    }

    private fun NavController.navigateIfAvailable(): Boolean =
        if (this.currentBackStackEntry?.destination?.id == R.id.addTodoFragment)
            this.navigateUp()
        else
            false

}