package com.mashaffer.mytapgame

/**
 * Interface defining callback methods for leaderboard operations.
 *
 * This interface facilitates communication between network/data layer and UI components
 * by providing callback methods that are invoked after asynchronous leaderboard operations
 * complete. Implementing classes can respond to these events appropriately.
 */
interface LeaderboardCallback {

    /**
     * Called when the leaderboard data has been successfully retrieved.
     *
     * @param data The list of Player objects representing the current leaderboard
     */
    fun onGetLeaderboardResult(data: List<Player>)

    /**
     * Called when an update to the leaderboard has been attempted.
     *
     * @param flag A boolean indicating whether the update was successful
     * @return The same flag value, potentially for chaining operations
     */
    fun onUpdateLeaderboardResult(flag: Boolean): Boolean

    /**
     * Called when an error occurs during a leaderboard operation.
     *
     * @param errorMessage A descriptive message about the error that occurred
     */
    fun onError(errorMessage: String)

    /**
     * Sideboard
     * fun onUsernameExists()
     */
}