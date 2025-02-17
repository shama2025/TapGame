package com.mashaffer.mytapgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Data class for leaderboard entries
data class LeaderboardEntry(
    val username: String,
    val taps: Int
)

// Adapter for displaying leaderboard entries
class LeaderboardAdapter(private val dataSet: List<LeaderboardActivity.tempLeaderboardList>) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.leaderBoard)

        // Bind data to view
        fun bind(entry: LeaderboardActivity.tempLeaderboardList) {
            textView.text = "${entry.username}: ${entry.taps} taps"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.leaderboard_activity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}