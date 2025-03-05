package com.mashaffer.mytapgame

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson


class MainActivity : AppCompatActivity(), LeaderboardCallback  {
    private lateinit var main:ConstraintLayout
    private val tapBtn: ImageButton by lazy { findViewById(R.id.imgBtn) }
    private val numTaps: TextView by lazy { findViewById(R.id.numTaps) }
    private val highScore: TextView by lazy { findViewById(R.id.high_score) }
    private val mainNav: BottomNavigationView by lazy { findViewById(R.id.main_navigation) }
    private val usernameView: TextView by lazy {findViewById(R.id.username_view)}
    private val util:Util = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        util.getLeaderboard(this)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup() {
        // Prompt user with input to get usernmae if none exists
        if(!hasUserName()){
           getUserName()
        }

        //utilCls.loadUserName()
        loadUserName()
        val highScoreTaps = getHighScore()
        // var taps = getLastTaps()
        var taps = 0
        numTaps.text = "Taps: $taps"
        highScore.text = "High Score: $highScoreTaps"

        tapBtn.setOnClickListener {
            taps = taps + 1
            when {
                taps >= highScoreTaps -> {
                    highScore.text = "High Score: $taps"
                    writeToHighScore(taps)
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
                        val playerBundle = Bundle().apply {
                            putString("Username", usernameView.text.toString())
                            putInt("Taps", taps)
                        }
                        startActivity(Intent(this, LeaderboardActivity::class.java).putExtras(playerBundle).apply {
                            action = Intent.ACTION_VIEW
                        })
                    } catch (e: ActivityNotFoundException) {
                        Log.e("BottomNav", "Error opening Leaderboard", e)
                    }
                }
            }
        }
    }


    private fun writeToHighScore(newHighScore: Int): Unit {
        util.getSharedPrefs(this)?.edit()?.putInt(getString(R.string.current_high_score_key), newHighScore)?.apply()
    }

    private fun getHighScore(): Int = util.getSharedPrefs(this)?.getInt(getString(R.string.current_high_score_key), 0) ?: 0

    private fun hasUserName(): Boolean {
        return util.getSharedPrefs(this)?.getString(getString(R.string.username_val), "")?.isNotEmpty() ?: false
    }

    private fun loadUserName(): Unit{
        usernameView.width = "Username: ${util.getSharedPrefs(this)?.getString(getString(R.string.username_val),"")}".length
        usernameView.text = "Username: ${util.getSharedPrefs(this)?.getString(getString(R.string.username_val),"")}"
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
//                        if(onUsernameExists()) {
                            // Save the username
                            util.getSharedPrefs(this)?.edit()?.putString(getString(R.string.username_val), username)?.apply()

                            Log.i("Alert Dialog", "Username was saved!")
                            dialog.dismiss()
                       // }
                    } else {
                        usernameEditText?.error = "Username input cannot be empty/username is taken"
                    }
                } catch (e: Exception) {
                    Log.e("AlertDialog", "Error handling username submission", e)
                    Toast.makeText(this, "Error saving username", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, id -> dialog.dismiss() }

        builder.create().show()
    }


    override fun onGetLeaderboardResult(players: List<Player>) {
        val editor = util.getSharedPrefs(this).edit()
        players.forEachIndexed { index, player ->
            val json = Gson().toJson(player) // Serialize to proper JSON
            editor.putString("player_$index", json)
        }
        editor.apply()
    }

    override fun onUpdateLeaderboardResult(flag: Boolean): Boolean{
        TODO("here to fulfill interface requirements")
    }

    override fun onError(errorMessage: String) {
        // Handle error here
        Log.e("Activity", "Error fetching leaderboard: $errorMessage")
    }

    /*** Side board functions (functions that I may implement later due to changes in user demands)*/
//    private fun writeCurrentTaps(currentTaps: Int) {
//        getSharedPrefs()?.edit()?.putInt(getString(R.string.current_taps_key), currentTaps)?.apply()
//    }
//private fun getLastTaps() = getSharedPrefs()?.getInt(getString(R.string.current_taps_key), 0) ?: 0
//    override fun onUsernameExists(flag: Boolean): Boolean{
//        Log.i("Main", "Response $flag")
//        return flag
//    }
}