package com.example.vinilosgrupo3.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.vinilosgrupo3.R
import com.example.vinilosgrupo3.databinding.ActivityMainBinding
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import java.util.zip.Inflater
import com.example.vinilosgrupo3.ui.AlbumFragment

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
        // Make sure actions in the ActionBar get propagated to the NavController
        Log.d("act", navController.toString())
        setSupportActionBar(findViewById(R.id.my_toolbar))
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}