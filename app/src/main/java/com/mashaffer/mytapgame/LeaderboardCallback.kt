package com.mashaffer.mytapgame

interface LeaderboardCallback {
    fun onResult(data: String)
    fun onError(errorMessage: String)
}