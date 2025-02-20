package com.mashaffer.mytapgame

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class LeaderboardActivity : AppCompatActivity() {
    companion object{
    }

    // Populate the view
    data class tempLeaderboardList(val username: String, val taps: Int) : Comparable<Any> {
        override fun compareTo(other: Any): Int {
            TODO("Not yet implemented")
        }
    }

    private val leaderBoardView: RecyclerView by lazy {findViewById(R.id.leaderBoard) }
    private val mainNav: BottomNavigationView by lazy { findViewById(R.id.main_navigation) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.leaderboard_activity)
        setup()
    }

    private fun setup() {
        // Will populate activites text field with array of usernames, and taps
        initLeaderBoard()

        // Array will need sorted by tap, or find alternative
        mainNav.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.home_page_btn -> {try{
                    startActivity(Intent(this, MainActivity::class.java).apply{
                        action = Intent.ACTION_MAIN
                    })
                    Log.i("BottomNav", "You clicked Home")
                }catch (e: ActivityNotFoundException){
                    Log.e("BottomNav", "Error opening Home page", e)

                }

                }
                R.id.leaderboard_btn -> Log.i("BottomNav", "You clicked Leaderboard")

                }
            }
        }

    private fun initLeaderBoard() {
        // Will initialize leader board on setup
        val players = listOf(
            tempLeaderboardList("PLayer 1", 50),
            tempLeaderboardList("Player 2", 500),
            tempLeaderboardList("Player 3", 53)
        )
        val sortedPlayers = players.sortedByDescending { it.taps }
        val customAdapter = LeaderboardAdapter(sortedPlayers)
        val recyclerView: RecyclerView = findViewById(R.id.leaderBoard)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
    }
}
