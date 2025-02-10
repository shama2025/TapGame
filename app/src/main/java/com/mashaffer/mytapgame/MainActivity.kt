package com.mashaffer.mytapgame


import android.content.Context
import android.os.Bundle
import android.util.Log
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
    private lateinit var highScore: TextView; // Records the days logged into the app (not idle)
    private lateinit var mainNav: BottomNavigationView // App Navigation

    // Figure out how to implement saving game data when app closes
    private var savedHighScore:Int = 0
    private val activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tapBtn = findViewById(R.id.imgBtn);
        numTaps = findViewById(R.id.numTaps);
        mainNav = findViewById(R.id.main_navigation);
        highScore = findViewById(R.id.high_score);
        setup();
    }

    private fun setup(){
        var highScoreTaps: Int = getHighScore()
        var taps: Int = getLastTaps();
        highScore.text = "High Score: ${highScoreTaps}"



        tapBtn.setOnClickListener({
            taps++;
            if(taps >= highScoreTaps){
                highScore.text = "High Score: ${taps}"
                writeToHighScore(taps)
            }
            numTaps.text = "Taps: ${taps}";
        })

        mainNav.setOnItemReselectedListener{ item ->
            when (item.itemId) {
                R.id.home_page_btn -> {
                    Log.i("BottomNav", "You clicked Home")
                    true
                }
                R.id.leaderboard_btn -> {
                    Log.i("BottomNav", "You clicked Leaderboard")
                    writeCurrentTaps(taps)
                    true
                }
//                R.id.status_btn -> {
//                    Log.i("BottomNav", "You clicked Status")
//                    true
//                }
                else -> false
            }
        }
    }

    private fun writeCurrentTaps(currentTaps: Int) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt(getString(R.string.curernt_taps), currentTaps)
            apply()
        }
    }

    private fun writeToHighScore(newHighScore:Int) {
        // Will write to the strings.xml file using the shared pref
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt(getString(R.string.current_high_score_key), newHighScore)
            apply()
        }
    }

    private fun getHighScore(): Int{
        // Using the shared prefs library, will get the current highscore that is stored
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return 0
        val defaultValue = resources.getInteger(R.integer.saved_high_score_val)
        val highScore = sharedPref.getInt(getString(R.string.current_high_score_key), defaultValue)
        return highScore
    }

    private fun getLastTaps(): Int{
        // Using the shared prefs library, will get the current highscore that is stored
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return 0
        val defaultValue = resources.getInteger(R.integer.saved_last_taps)
        val lastTaps = sharedPref.getInt(getString(R.string.current_high_score_key), defaultValue)
        return lastTaps
    }

}