package com.mashaffer.mytapgame

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class LeaderboardActivity : AppCompatActivity(), LeaderboardCallback {


    // Populate the view
    data class tempLeaderboardList(val username: String, val taps: Int) : Comparable<Any> {
        override fun compareTo(other: Any): Int {
            TODO("Not yet implemented")
        }
    }

//private val leaderBoardView: RecyclerView by lazy {findViewById(R.id.leaderBoard) }
    private val mainNav: BottomNavigationView by lazy { findViewById(R.id.main_navigation) }
    private var username: String? = ""
    private val util:Util = Util()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.leaderboard_activity)

        util.getLeaderboard(this)
        setup()
    }

   private fun setup(): Unit {
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

   private fun initLeaderBoard(): Unit {
        val username = intent.getStringExtra("Username")?.takeIf { it.isNotEmpty() } ?: ""

        // Will initialize leader board on setup
//        val players = listOf(
//            tempLeaderboardList("PLayer 1", 50),
//            tempLeaderboardList("Player 2", 500),
//            tempLeaderboardList("Player 3", 53)
//        )
       //val players = utilCls.fetchLeaderboard()

      // Log.i("Leaderboard", "Here is data from the API ${players}")


//        val sortedPlayers = players.sortedByDescending { it.taps }
//        val customAdapter = LeaderboardAdapter(sortedPlayers)
//        val recyclerView: RecyclerView = findViewById(R.id.leaderBoard)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = customAdapter
    }

    override fun onResult(data: String) {
        // Handle the successful response here
        Log.i("Activity", "Received leaderboard data: $data")
        // Send data to shared preferences on app load
        // Then in activity call the data to populate recyclerview
        

    }

    override fun onError(errorMessage: String) {
        // Handle error here
        Log.e("Activity", "Error fetching leaderboard: $errorMessage")
    }
}
