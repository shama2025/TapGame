package com.mashaffer.mytapgame

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class Util : ViewModel() {
    val leaderboardApi =
        LeaderboardApiServiceHelper.getInstance().create(LeaderbardApiServiceInterface::class.java)

    // Sideboard object to hold future features
    object Sideboard {
        // Feature flags for future implementations
        var enableUsernameCheck = false
        var enableAdvancedStats = false
        var enableChallengeMode = false

        // Function to toggle features based on user demands
        fun toggleFeature(featureName: String, isEnabled: Boolean) {
            when (featureName.lowercase()) {
                "usernamecheck" -> enableUsernameCheck = isEnabled
                "advancedstats" -> enableAdvancedStats = isEnabled
                "challengemode" -> enableChallengeMode = isEnabled
            }
        }

        // Check if a specific feature is enabled
        fun isFeatureEnabled(featureName: String): Boolean {
            return when (featureName.lowercase()) {
                "usernamecheck" -> enableUsernameCheck
                "advancedstats" -> enableAdvancedStats
                "challengemode" -> enableChallengeMode
                else -> false
            }
        }
    }

    fun getLeaderboard(callback: MainActivity) {
        viewModelScope.launch {
            try {
                val result = leaderboardApi.getLeaderboard()
                if (result.isSuccessful) {
                    result.body()?.let { callback.onGetLeaderboardResult(it) }
                }
            } catch (e: Exception) {
                Log.i("Util", "Error when accessing PythonAnywhere API: ${e.message}}")
                callback.onError(e.message.toString())
            }
        }
    }

    fun updateLeaderboard(callback: MainActivity, player: Player) {
        viewModelScope.launch {
            try {
                val result = leaderboardApi.updateLeaderboard(player.username, player.taps)
                if (result.isSuccessful) {
                    result.body()?.let { callback.onUpdateLeaderboardResult(true) }
                }
            } catch (e: Exception) {
                Log.i("Util", "Error when accessing Flask API: ${e.message}}")
                callback.onUpdateLeaderboardResult(false)
                callback.onError(e.message.toString())
            }
        }
    }

    fun getSharedPrefs(context: Context) =
        context.getSharedPreferences("Player", Context.MODE_PRIVATE)


    // Commented out function preserved for future implementation
    // Can be enabled via Sideboard.toggleFeature("usernamecheck", true)
//    fun checkUsername(callback: MainActivity, username: String) {
//        if (!Sideboard.isFeatureEnabled("usernamecheck")) {
//            Log.i("Util", "Username check feature is disabled")
//            return
//        }
//
//        viewModelScope.launch {
//            try {
//                val result = leaderboardApi.checkUsername(username)
//                if (result.isSuccessful) {
//                    // Username was added
//                    result.body()?.let { callback.onUsernameExists(true) }
//                } else {
//                    // Username already exists
//                    result.body()?.let { callback.onUsernameExists(false) }
//                }
//            } catch (e: Exception) {
//                Log.i("Util", "Error when accessing Flask API: ${e.message}}")
//                callback.onUpdateLeaderboardResult(false)
//                callback.onError(e.message.toString())
//            }
//        }
//    }

}