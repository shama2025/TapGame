package com.mashaffer.mytapgame

data class Leaderboard(
    val place: Int = 0,
    val taps: Int = 0,
    val username: String = ""
) {
    companion object {
        fun empty(): Leaderboard = Leaderboard(place = 0, taps = 0, username = "Player")
        fun loading(): Leaderboard = Leaderboard(username = "Loading...")
        fun error(): Leaderboard = Leaderboard(username = "Error")
    }
}