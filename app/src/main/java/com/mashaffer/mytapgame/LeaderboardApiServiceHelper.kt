package com.mashaffer.mytapgame

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LeaderboardApiServiceHelper {
    private const val baseUrl = "https://mashaffer.pythonanywhere.com"

    fun getInstance(): Retrofit{
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }


}