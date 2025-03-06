package com.mashaffer.mytapgame

/**
 * Represents a player in the tap game with their score and ranking information.
 *
 * This data class encapsulates all essential player information used throughout
 * the application, particularly for leaderboard functionality. It's designed to
 * be serializable for storage in SharedPreferences and transmission to/from the server.
 *
 * @property place The player's position in the leaderboard ranking (1 for first place, etc.)
 * @property username The unique identifier for the player, displayed in the UI
 * @property taps The number of taps the player has accumulated, used to determine ranking
 */
data class Player(
    val place: Int,
    val username: String,
    val taps: Int
)