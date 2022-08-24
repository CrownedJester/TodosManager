package com.crownedjester.soft.todosmanager

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController
    }

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

        return if (item.itemId == android.R.id.home && navController.currentBackStackEntry?.destination?.id == R.id.addTodoFragment) {
            navController.navigateUp()
        } else super.onOptionsItemSelected(
            item
        )

    }
}