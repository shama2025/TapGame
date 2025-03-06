package com.mashaffer.mytapgame

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * LeaderboardActivity displays the top players and their scores.
 * It allows refreshing the leaderboard data and navigating to other app sections.
 */
class LeaderboardActivity : AppCompatActivity(), LeaderboardCallback {

    // Lazy-initialized UI components for better performance
    private val mainNav: BottomNavigationView by lazy { findViewById(R.id.main_navigation) }
    private val refreshBtn: Button by lazy { findViewById(R.id.refresh_btn) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.leaderBoard) }

    // Utility objects
    private val gson = Gson()
    private val util = Util()

    // Tag for logging
    private companion object {
        private const val TAG = "LeaderboardActivity"
        private const val MAX_LEADERBOARD_ENTRIES = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.leaderboard_activity)

        // Initialize the UI components and set up event listeners
        setup()
    }

    /**
     * Sets up the UI by initializing the leaderboard and configuring UI components
     * including bottom navigation and refresh button
     */
    private fun setup() {
        // Load and display initial leaderboard data
        initLeaderBoard()

        // Set up bottom navigation listener
        setupBottomNavigation()

        // Set up refresh button functionality using coroutines
        setupRefreshButton()
    }

    /**
     * Configures the bottom navigation view with appropriate actions
     */
    private fun setupBottomNavigation() {
        mainNav.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.home_page_btn -> {
                    try {
                        startActivity(Intent(this, MainActivity::class.java).apply {
                            action = Intent.ACTION_MAIN
                        })
                    } catch (e: ActivityNotFoundException) {
                        Log.e(TAG, "Error opening Home page", e)
                    }
                }

                R.id.leaderboard_btn -> {
                    // Already on leaderboard, no action needed
                    Log.i(TAG, "User clicked Leaderboard while already on Leaderboard screen")
                }
            }
        }
    }

    /**
     * Sets up the refresh button functionality using coroutines
     */
    private fun setupRefreshButton() {
        lifecycleScope.launch {
            waitForButtonClick(refreshBtn)

            // Extract player data from intent
            val player = getPlayerFromIntent()

            // Update leaderboard with current player data
            util.updateLeaderboard(MainActivity(), player)

            // Refresh leaderboard data
            val updatedPlayers = util.getLeaderboard(MainActivity())

        }
    }

    /**
     * Extracts player information from the intent
     * @return A Player object containing the username and taps from the intent
     */
    private fun getPlayerFromIntent(): Player {
        return Player(
            username = intent.getStringExtra("Username")?.takeIf { it.isNotEmpty() } ?: "",
            taps = intent.getIntExtra("Taps", 0),
            place = 0 // Place will be calculated during sorting
        )
    }

    /**
     * Updates the leaderboard UI with the provided player data
     * @param players List of players to display in the leaderboard
     */
    private fun updateLeaderboardDisplay(players: List<Player>) {
        val sortedPlayers = players.sortedByDescending { it.taps }
        val customAdapter = LeaderboardAdapter(sortedPlayers)
        recyclerView.adapter = customAdapter
    }

    /**
     * Suspends the coroutine until the button is clicked
     * This turns the button click into a suspending function
     * @param button The button to wait for a click on
     */
    private suspend fun waitForButtonClick(button: Button) =
        suspendCancellableCoroutine { continuation ->
            val clickListener = View.OnClickListener {
                continuation.resume(Unit)
            }
            button.setOnClickListener(clickListener)
            continuation.invokeOnCancellation {
                button.setOnClickListener(null)
            }
        }

    /**
     * Initializes the leaderboard by loading player data from SharedPreferences
     * and displaying it in the RecyclerView
     */
    private fun initLeaderBoard() {
        // Load player data from SharedPreferences
        val players = loadPlayersFromSharedPreferences()

        // Sort players by number of taps (descending)
        val sortedPlayers = players.sortedByDescending { it.taps }

        // Set up the RecyclerView with the adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = LeaderboardAdapter(sortedPlayers)
    }

    /**
     * Loads player data from SharedPreferences
     * @return A list of Player objects
     */
    private fun loadPlayersFromSharedPreferences(): List<Player> {
        val players = mutableListOf<Player>()
        val sharedPrefs = util.getSharedPrefs(this)

        for (index in 0 until MAX_LEADERBOARD_ENTRIES) {
            val json = sharedPrefs.getString("player_$index", "{}")
            try {
                val player = gson.fromJson(json, Player::class.java) ?: Player(0, "", 0)
                players.add(player)
            } catch (e: Exception) {
                Log.e(TAG, "Error parsing player data at index $index", e)
                players.add(Player(0, "", 0)) // Add default player on error
            }
        }

        return players
    }

    // LeaderboardCallback implementation methods

    /**
     * Callback for when the leaderboard is updated
     * @param flag Whether the update was successful
     * @return The same flag value
     */
    override fun onUpdateLeaderboardResult(flag: Boolean): Boolean {
        return flag
    }

    /**
     * Callback for when leaderboard data is retrieved
     * @param data The list of players from the leaderboard
     */
    override fun onGetLeaderboardResult(data: List<Player>) {
        // Update the UI with the new leaderboard data
        updateLeaderboardDisplay(data)
    }

    /**
     * Callback for handling errors
     * @param errorMessage The error message
     */
    override fun onError(errorMessage: String) {
        Log.e(TAG, "Leaderboard error: $errorMessage")
        // In a production app, display an error message to the user
    }
}