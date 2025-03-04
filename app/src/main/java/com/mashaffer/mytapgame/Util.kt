package com.mashaffer.mytapgame

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class Util: ViewModel() {
    val leaderboardApi = LeaderboardApiServiceHelper.getInstance().create(LeaderbardApiServiceInterface::class.java)
    fun getLeaderboard(callback: MainActivity) {
    viewModelScope.launch {
        try{
            val result = leaderboardApi.getLeaderboard()
            if(result.isSuccessful){
                result.body()?.let { callback.onGetLeaderboardResult(it) }
            }
        }catch (e:Exception){
            Log.i("Util", "Error when accessing Flask API: ${e.message}}")
            callback.onError(e.message.toString())
        }

    }
    }

    fun updateLeaderboard(callback: MainActivity, player: Player){
       viewModelScope.launch {
           try{
               val result = leaderboardApi.updateLeaderboard(player.username,player.taps)
               if(result.isSuccessful){
                   result.body()?.let { callback.onUpdateLeaderboardResult(true) }
               }
           }catch (e:Exception){
               Log.i("Util", "Error when accessing Flask API: ${e.message}}")
               callback.onUpdateLeaderboardResult(false)
               callback.onError(e.message.toString())
           }
       }
    }

//    fun checkUsername(callback: MainActivity, username: String){
//        // If true then username is added
//        // If false throw up toast stating username is not valid
//    viewModelScope.launch {
//        try{
//            val result = leaderboardApi.checkUsername(username)
//            if(result.isSuccessful){
//                // Username was added
//                result.body()?.let { callback.onUsernameExists(true) }
//            }else{
//                // Username already exists
//                result.body()?.let { callback.onUsernameExists(false) }
//            }
//        }catch (e:Exception){
//            Log.i("Util", "Error when accessing Flask API: ${e.message}}")
//            callback.onUpdateLeaderboardResult(false)
//            callback.onError(e.message.toString())
//        }
//    }
//    }

    fun getSharedPrefs(context: Context) = context.getSharedPreferences("Player",Context.MODE_PRIVATE)

}