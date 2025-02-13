package com.mashaffer.mytapgame

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val tapBtn: ImageButton by lazy { findViewById(R.id.imgBtn) }
    private val numTaps: TextView by lazy { findViewById(R.id.numTaps) }
    private val highScore: TextView by lazy { findViewById(R.id.high_score) }
    private val mainNav: BottomNavigationView by lazy { findViewById(R.id.main_navigation) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup() {
        val highScoreTaps = getHighScore()
        var taps = getLastTaps()
        highScore.text = "High Score: $highScoreTaps"

        tapBtn.setOnClickListener {
            taps = taps!! + 1
            when {
                taps!! >= highScoreTaps -> {
                    highScore.text = "High Score: $taps"
                    writeToHighScore(taps!!)
                }
            }
            numTaps.text = "Taps: $taps"
        }

        mainNav.setOnItemReselectedListener { item ->
            writeCurrentTaps(taps!!)
            when (item.itemId) {
                R.id.home_page_btn -> Log.i("BottomNav", "You clicked Home")
                R.id.leaderboard_btn -> {
                    try {
                        Log.i("BottomNav", "You clicked Leaderboard")
                        startActivity(Intent(this, LeaderboardActivity::class.java).apply {
                            action = Intent.ACTION_VIEW
                        })
                    } catch (e: ActivityNotFoundException) {
                        Log.e("BottomNav", "Error opening Leaderboard", e)
                    }
                }
            }
        }
    }

    private fun getSharedPrefs() = getPreferences(Context.MODE_PRIVATE)

    private fun writeCurrentTaps(currentTaps: Int) {
        getSharedPrefs()?.edit()?.putInt(getString(R.string.current_taps_key), currentTaps)?.apply()
    }

    private fun writeToHighScore(newHighScore: Int) {
        getSharedPrefs()?.edit()?.putInt(getString(R.string.current_high_score_key), newHighScore)?.apply()
    }

    private fun getHighScore() = getSharedPrefs()?.getInt(getString(R.string.current_high_score_key), 0) ?: 0
    private fun getLastTaps() = getSharedPrefs()?.getInt(getString(R.string.current_taps_key),
