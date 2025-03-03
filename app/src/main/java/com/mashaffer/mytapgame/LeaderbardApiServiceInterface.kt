package com.mashaffer.mytapgame

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT

interface LeaderbardApiServiceInterface {
    @GET("/get_leaderboard")
    suspend fun getLeaderboard(): Response<List<Player>>

    @PUT("up_leaderboard")
    suspend fun updateLeaderboard(): String
}