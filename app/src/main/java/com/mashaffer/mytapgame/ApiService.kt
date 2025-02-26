package com.mashaffer.mytapgame

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.cio.Response


public class ApiService(){
    private val BASE_URL = "http://127.0.0.1:5000"

    suspend fun getLeaderboard(): List<Leaderboard> {
        val client = HttpClient(CIO)
        val res: HttpResponse = client.request("${BASE_URL}/get_leaderboard")
        client.close()
        return res.body() ?: emptyList()
    }

    suspend fun updateLeaderboard(taps: Int, username: String): Boolean{
        val client = HttpClient(CIO)
        val res: HttpResponse = client.post("$BASE_URL/up_leaderboard"){
           url{
               parameters.append(taps.toString(),username)
           }
        }
        client.close()
        return res.body()
    }
}
