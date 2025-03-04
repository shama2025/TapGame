package com.mashaffer.mytapgame

interface LeaderboardCallback {
    fun onGetLeaderboardResult(data: List<Player>)
    fun onUpdateLeaderboardResult(flag: Boolean): Boolean
//    fun onUsernameExists(flag:Boolean): Boolean
    fun onError(errorMessage: String)
}