package com.mashaffer.mytapgame

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class Util: ViewModel() {
    val leaderboardApi = LeaderboardApiServiceHelper.getInstance().create(LeaderbardApiServiceInterface::class.java)
    fun getLeaderboard(callback: LeaderboardCallback) {
    viewModelScope.launch {
        try{
            val result = leaderboardApi.getLeaderboard()
            if(result.isSuccessful){
                Log.i("Util", "Here is the result from the API: ${result.body().toString()}")
                callback.onResult(result.body().toString())
            }
        }catch (e:Exception){
            Log.i("Util", "Error when accessing Flask API: ${e.message}}")
            callback.onError(e.message.toString())
        }

    }
    }
}