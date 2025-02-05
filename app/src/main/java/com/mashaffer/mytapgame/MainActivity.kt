package com.mashaffer.mytapgame

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity";
    }

    // Activity Level Variables
    private lateinit var tapBtn: ImageButton; // Image button for tapping
    private lateinit var numTaps: TextView; // Records the number of taps
    private lateinit var daysLoggedIn: TextView; // Records the days logged into the app (not idle)
    private lateinit var main_nav: BottomNavigationView // App Navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tapBtn = findViewById(R.id.imgBtn);
        numTaps = findViewById(R.id.numTaps);
        main_nav = findViewById(R.id.main_navigation);
        setup();
    }

    private fun setup(){
        var taps: Int = 0;
        tapBtn.setOnClickListener({
            taps++;
            numTaps.text = "Taps: ${taps}";
        })

        main_nav.setOnItemReselectedListener{ item ->
            when (item.itemId) {
                R.id.home_page_btn -> {
                    Log.i("BottomNav", "You clicked Home")
                    true
                }
                R.id.leaderboard_btn -> {
                    Log.i("BottomNav", "You clicked Leaderboard")
                    true
                }
                R.id.status_btn -> {
                    Log.i("BottomNav", "You clicked Status")
                    true
                }
                else -> false
            }
        }
    }
}