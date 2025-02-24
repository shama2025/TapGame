package com.mashaffer.mytapgame

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val tapBtn: ImageButton by lazy { findViewById(R.id.imgBtn) }
    private val numTaps: TextView by lazy { findViewById(R.id.numTaps) }
    private val highScore: TextView by lazy { findViewById(R.id.high_score) }
    private val mainNav: BottomNavigationView by lazy { findViewById(R.id.main_navigation) }
    private val usernameView: TextView by lazy {findViewById(R.id.username_view)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup() {
        // Prompt user with input to get usernmae if none exists
        if(!hasUserName()){
           getUserName()
        }

        loadUserName()
        val highScoreTaps = getHighScore()
        // var taps = getLastTaps()
        var taps = 0
        numTaps.text = "Taps: $taps"
        highScore.text = "High Score: $highScoreTaps"

        tapBtn.setOnClickListener {
            taps = taps + 1
            when {
                taps!! >= highScoreTaps -> {
                    highScore.text = "High Score: $taps"
                    writeToHighScore(taps!!)
                }
            }
            numTaps.text = "Taps: $taps"
        }

        mainNav.setOnItemReselectedListener { item ->
            //writeCurrentTaps(taps!!)
            when (item.itemId) {
                R.id.home_page_btn -> Log.i("BottomNav", "You clicked Home")
                R.id.leaderboard_btn -> {
                    try {
                        Log.i("BottomNav", "You clicked Leaderboard")
                        // To update the leaderboard I can pass tap num through the intent
                        // And then check to see if the value exists in the top ten
                        // If it does then don't adjust array
                        // If it doesn't then append to end of array and display place value
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

    private fun writeToHighScore(newHighScore: Int): Unit {
        getSharedPrefs()?.edit()?.putInt(getString(R.string.current_high_score_key), newHighScore)?.apply()
    }

    private fun getHighScore(): Int = getSharedPrefs()?.getInt(getString(R.string.current_high_score_key), 0) ?: 0

    private fun hasUserName(): Boolean {
        return getSharedPrefs()?.getString("Username", "")?.isNotEmpty() ?: false
    }

    private fun loadUserName(): Unit{
        usernameView.width = "Username: ${getSharedPrefs()?.getString("Username","")}".length
        usernameView.text = "Username: ${getSharedPrefs()?.getString("Username","")}"
    }

    private fun getUserName(): Unit {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.username_input, null)
        val usernameEditText = dialogView.findViewById<EditText>(R.id.username_input_field)

        builder.setView(dialogView)
            .setPositiveButton("Submit") { dialog, _ ->
                try {
                    val username = usernameEditText?.text?.toString() ?: ""
                    if (username.isNotEmpty()) {
                        // Save the username
                        getSharedPrefs()?.edit()?.putString("Username", username)?.apply()

                        Log.i("Alert Dialog", "Username was saved!")
                        dialog.dismiss()
                    } else {
                        usernameEditText?.error = "Username cannot be empty"
                    }
                } catch (e: Exception) {
                    Log.e("AlertDialog", "Error handling username submission", e)
                    Toast.makeText(this, "Error saving username", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, id -> dialog.dismiss() }

        builder.create().show()
    }

    /*** Side board functions (functions that I may implement later due to changes in user demands)*/
//    private fun writeCurrentTaps(currentTaps: Int) {
//        getSharedPrefs()?.edit()?.putInt(getString(R.string.current_taps_key), currentTaps)?.apply()
//    }
//private fun getLastTaps() = getSharedPrefs()?.getInt(getString(R.string.current_taps_key), 0) ?: 0
}