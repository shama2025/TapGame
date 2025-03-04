package com.mashaffer.mytapgame

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class LeaderboardActivity : AppCompatActivity(), LeaderboardCallback {

//private val leaderBoardView: RecyclerView by lazy {findViewById(R.id.leaderBoard) }
    private val mainNav: BottomNavigationView by lazy { findViewById(R.id.main_navigation) }
    private val refreshBtn: ImageButton by lazy {findViewById(R.id.refresh_btn)}
    private var username: String? = ""
    private val gson = Gson()
    private val util: Util = Util()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.leaderboard_activity)

        setup()
    }

   private fun setup(): Unit {
        initLeaderBoard()

        mainNav.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.home_page_btn -> {try{
                    startActivity(Intent(this, MainActivity::class.java).apply{
                        action = Intent.ACTION_MAIN
                    })
                }catch (e: ActivityNotFoundException){
                    Log.e("BottomNav", "Error opening Home page", e)

                }

                }
                R.id.leaderboard_btn -> Log.i("BottomNav", "You clicked Leaderboard")

                }
            }

            refreshBtn.setOnClickListener({
                val player: Player = Player(
                    username = intent.getStringExtra("Username")?.takeIf { it.isNotEmpty() } ?: "",
                    taps = intent.getIntExtra("Taps",0),
                    place = 0
                )
                util.updateLeaderboard(MainActivity(),player)
                util.getLeaderboard(MainActivity())
            })

        }



    private fun initLeaderBoard(): Unit {
        val username = intent.getStringExtra("Username")?.takeIf { it.isNotEmpty() } ?: ""
        val players = mutableListOf<Player>()
        for (index in 0 until 10) {
            val json = util.getSharedPrefs(this).getString("player_$index", "{}")
            val player = gson.fromJson(json, Player::class.java)
            players.add(player)
        }

        val sortedPlayers = players.sortedByDescending { it.taps}
        val customAdapter = LeaderboardAdapter(sortedPlayers)
        val recyclerView: RecyclerView = findViewById(R.id.leaderBoard)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
    }

    override fun onUpdateLeaderboardResult(flag: Boolean): Boolean {
        return flag
    }

    /**
     * Used to fulfill interface requirements
     */
    override fun onGetLeaderboardResult(data: List<Player>) {
        TODO("Not yet implemented")
    }


//    override fun onUsernameExists(flag: Boolean): Boolean {
//        TODO("Not yet implemented")
//    }

    override fun onError(errorMessage: String) {
        TODO("Not yet implemented")
    }


}
