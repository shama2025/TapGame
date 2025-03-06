package com.mashaffer.mytapgame

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface LeaderbardApiServiceInterface {
    // Route for getting leaderboard
    @GET("/get_leaderboard")
    suspend fun getLeaderboard(): Response<List<Player>>

    // Route for updateing leaderboard
    @PUT("/up_leaderboard")
    suspend fun updateLeaderboard(@Query("username") username: String,@Query("taps") taps: Int): Response<String>

    // Route for checking username status
//   @GET("/username_exists")
//   suspend fun checkUsername(@Query("username") username: String):  Response<String>
}