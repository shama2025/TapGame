package com.mashaffer.mytapgame

import android.content.ActivityNotFoundException
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
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

/**
 * MainActivity is the primary entry point for the tap game.
 * It handles the main game mechanics, user interaction, and navigation to other screens.
 */
class MainActivity : AppCompatActivity(), LeaderboardCallback {
    // UI Components
    private lateinit var main: ConstraintLayout
    private val tapBtn: ImageButton by lazy { findViewById(R.id.imgBtn) }
    private val numTaps: TextView by lazy { findViewById(R.id.numTaps) }
    private val highScore: TextView by lazy { findViewById(R.id.high_score) }
    private val mainNav: BottomNavigationView by lazy { findViewById(R.id.main_navigation) }
    private val usernameView: TextView by lazy { findViewById(R.id.username_view) }

    // Utility instance for shared functionality
    private val util: Util = Util()

    // Constants for logging
    private companion object {
        private const val TAG = "MainActivity"
    }

    /**
     * Initializes the activity, sets up the UI and loads necessary data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Fetch leaderboard data on startup
        util.getLeaderboard(this)

        setContentView(R.layout.activity_main)
        setup()
    }

    /**
     * Sets up the UI components and configures event listeners
     */
    private fun setup() {
        // Prompt user for username if none exists
        if (!hasUserName()) {
            getUserName()
        }

        // Load and display the username
        loadUserName()

        // Get and display the high score
        val highScoreTaps = getHighScore()
        var taps = 0

        // Update UI with initial values
        numTaps.text = "Taps: $taps"
        highScore.text = "High Score: $highScoreTaps"

        // Set up tap button click listener
        setupTapButton(highScoreTaps, taps)

        // Set up bottom navigation
        setupBottomNavigation(taps)
    }

    /**
     * Configures the tap button behavior
     *
     * @param highScoreTaps The current high score
     * @param taps The current number of taps
     */
    private fun setupTapButton(highScoreTaps: Int, initialTaps: Int) {
        var taps = initialTaps

        tapBtn.setOnClickListener {
            taps += 1

            // Update high score if current taps exceed it
            if (taps >= highScoreTaps) {
                highScore.text = "High Score: $taps"
                writeToHighScore(taps)
            }

            // Update tap counter display
            numTaps.text = "Taps: $taps"
        }
    }

    /**
     * Configures the bottom navigation behavior
     *
     * @param taps The current number of taps
     */
    private fun setupBottomNavigation(taps: Int) {
        mainNav.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.home_page_btn -> {
                    // Already on home page, log the event
                    Log.i(TAG, "User clicked Home while already on Home screen")
                }
                R.id.leaderboard_btn -> {
                    try {
                        Log.i(TAG, "Navigating to Leaderboard")

                        // Create bundle with player data
                        val playerBundle = Bundle().apply {
                            putString("Username", usernameView.text.toString().replace("Username: ", ""))
                            putInt("Taps", taps)
                        }

                        // Start the leaderboard activity with player data
                        startActivity(Intent(this, LeaderboardActivity::class.java)
                            .putExtras(playerBundle)
                            .apply {
                                action = Intent.ACTION_VIEW
                            }
                        )
                    } catch (e: ActivityNotFoundException) {
                        Log.e(TAG, "Error opening Leaderboard", e)
                    }
                }
            }
        }
    }

    /**
     * Saves a new high score to SharedPreferences
     *
     * @param newHighScore The new high score to save
     */
    private fun writeToHighScore(newHighScore: Int) {
        util.getSharedPrefs(this).edit()
            .putInt(getString(R.string.current_high_score_key), newHighScore)
            .apply()
    }

    /**
     * Retrieves the current high score from SharedPreferences
     *
     * @return The current high score, or 0 if none exists
     */
    private fun getHighScore(): Int =
        util.getSharedPrefs(this).getInt(getString(R.string.current_high_score_key), 0)

    /**
     * Checks if the user has previously set a username
     *
     * @return True if a username exists, false otherwise
     */
    private fun hasUserName(): Boolean {
        return util.getSharedPrefs(this)
            .getString(getString(R.string.username_val), "")
            ?.isNotEmpty() ?: false
    }

    /**
     * Loads and displays the current username
     */
    private fun loadUserName() {
        val username = util.getSharedPrefs(this).getString(getString(R.string.username_val), "")
        // Fix: Don't set width based on text length as this could cause UI issues
        usernameView.text = "Username: $username"
    }

    /**
     * Prompts the user to enter a username via dialog
     */
    private fun getUserName() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.username_input, null)
        val usernameEditText = dialogView.findViewById<EditText>(R.id.username_input_field)

        builder.setView(dialogView)
            .setPositiveButton("Submit") { dialog, _ ->
                try {
                    val username = usernameEditText?.text?.toString() ?: ""
                    if (username.isNotEmpty()) {
                        // Save the username
                        util.getSharedPrefs(this).edit()
                            .putString(getString(R.string.username_val), username)
                            .apply()

                        Log.i(TAG, "Username was saved: $username")
                        dialog.dismiss()

                        // Refresh the username display
                        loadUserName()
                    } else {
                        usernameEditText?.error = "Username input cannot be empty"
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error handling username submission", e)
                    Toast.makeText(this, "Error saving username", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }

    // LeaderboardCallback interface implementation

    /**
     * Callback for when leaderboard data is retrieved
     * Saves the player data to SharedPreferences
     *
     * @param players The list of players from the leaderboard
     */
    override fun onGetLeaderboardResult(players: List<Player>) {
        val editor = util.getSharedPrefs(this).edit()
        players.forEachIndexed { index, player ->
            val json = Gson().toJson(player)
            editor.putString("player_$index", json)
        }
        editor.apply()
        Log.d(TAG, "Leaderboard data saved: ${players.size} players")
    }

    /**
     * Placeholder implementation for interface compliance
     * To be implemented in future versions
     */
    override fun onUpdateLeaderboardResult(flag: Boolean): Boolean {
        // Not yet implemented
        return flag
    }

    /**
     * Handles errors that occur during leaderboard operations
     *
     * @param errorMessage The error message to log
     */
    override fun onError(errorMessage: String) {
        Log.e(TAG, "Error fetching leaderboard: $errorMessage")
        // Consider showing an error message to the user
    }

    /**
     * Sideboard for future development
     * Functions that may be implemented later due to changes in user demands
     */
    private object Sideboard {
        /**
         * Saves the current tap count to SharedPreferences
         *
         * @param context The activity context
         * @param util The utility class instance
         * @param currentTaps The current number of taps to save
         */
        fun writeCurrentTaps(context: MainActivity, util: Util, currentTaps: Int) {
            util.getSharedPrefs(context).edit()
                .putInt(context.getString(R.string.current_taps_key), currentTaps)
                .apply()
        }

        /**
         * Retrieves the last saved tap count from SharedPreferences
         *
         * @param context The activity context
         * @param util The utility class instance
         * @return The last saved tap count, or 0 if none exists
         */
        fun getLastTaps(context: MainActivity, util: Util): Int =
            util.getSharedPrefs(context).getInt(context.getString(R.string.current_taps_key), 0)

        /**
         * Callback for username existence check
         * Currently a placeholder for future implementation
         *
         * @param flag Whether the username exists
         * @return The flag value for chaining
         */
        fun onUsernameExists(flag: Boolean): Boolean {
            Log.i("Main", "Response $flag")
            return flag
        }
    }
}