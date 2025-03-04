package com.mashaffer.mytapgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LeaderboardAdapter(
    private val players: List<Player>?
) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHoler>() {
    companion object{
        private const val MARGIN_SIZE = 20
        private const val TAG = "LeaderboadAdapter"
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHoler {
        // Will display the leaderboard element
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.leaderboard_element, viewGroup, false)
        return ViewHoler(view)
    }

    // Number of players
    override fun getItemCount(): Int = players!!.size

    override fun onBindViewHolder(holder: ViewHoler, position: Int): Unit {
        holder.bind(position)
    }
    inner class ViewHoler(item: View): RecyclerView.ViewHolder(item){
        // Username of player
        val usernameTextView: TextView = item.findViewById(R.id.user_field)
        // Number of total taps player has
        val tapsTextView: TextView = item.findViewById(R.id.taps_field)
        // Index of player list
        val placeTextView: TextView = item.findViewById(R.id.place_field)

        fun bind(position: Int){
            tapsTextView.text = "Taps: ${String.format(players?.get(position)?.taps.toString())}"
            usernameTextView.text = "${players?.get(position)?.username}"
            placeTextView.text = "Place: ${(position + 1)}"
        }
    }
}
