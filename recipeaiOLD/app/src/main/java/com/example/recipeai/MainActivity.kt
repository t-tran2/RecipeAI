package com.example.recipeai

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipeai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("onCreateActivity", "onCreate lifecycle triggered")
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onStart(){
        super.onStart()
        Log.d("onStartActivity", "onStart lifecycle triggered")

    }

    override fun onResume(){
        super.onResume()
        Log.d("onResumeActivity", "onResume lifecycle triggered")

    }
    override fun onDestroy(){
        super.onDestroy()
        Log.d("onDestroyActivity", "onDestroy lifecycle triggered")

    }

    override fun onStop(){
        super.onStop()
        Log.d("onStopActivity", "onStop lifecycle triggered")

    }

    override fun onPause(){
        super.onPause()
        Log.d("onPauseActivity", "onPause lifecycle triggered")

    }










}