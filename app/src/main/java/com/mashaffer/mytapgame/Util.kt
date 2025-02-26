package com.mashaffer.mytapgame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch

public class Util(): ViewModel() {

    companion object{
        private val apiService: ApiService = ApiService()
        private val _leaderboardData = MutableLiveData<List<Leaderboard>>()
    }

    fun fetchLeaderboard(){
        viewModelScope.launch {
            try{
                val res = apiService.getLeaderboard()
                Log.i("Util", "This is the response body: ${res}")
                _leaderboardData.postValue(res)
            }catch(e:Exception){
                _leaderboardData.postValue(emptyList())
            }
        }
    }

}