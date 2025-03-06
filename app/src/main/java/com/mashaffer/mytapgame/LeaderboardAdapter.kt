package com.mashaffer.mytapgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for displaying player data in a RecyclerView for the leaderboard.
 * Handles the creation and binding of ViewHolders that display player information.
 *
 * @property players The list of Player objects to display in the leaderboard
 */
class LeaderboardAdapter(
    private val players: List<Player>?
) : RecyclerView.Adapter<LeaderboardAdapter.PlayerViewHolder>() {

    companion object {
        private const val MARGIN_SIZE = 20
        private const val TAG = "LeaderboardAdapter" // Fixed typo in tag name
    }

    /**
     * Creates a new ViewHolder when needed by the RecyclerView
     *
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View (not used in this implementation)
     * @return A new PlayerViewHolder that holds a view of the leaderboard item
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        // Inflate the leaderboard element layout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.leaderboard_element, parent, false)
        return PlayerViewHolder(view)
    }

    /**
     * Returns the total number of items in the data set
     *
     * @return The number of players in the leaderboard, or 0 if the list is null
     */
    override fun getItemCount(): Int = players?.size ?: 0

    /**
     * Binds the data at the specified position to the ViewHolder
     *
     * @param holder The ViewHolder to bind data to
     * @param position The position of the item in the data set
     */
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(position)
    }

    /**
     * ViewHolder class that represents each item in the leaderboard
     * Holds references to the views within the item layout and binds data to them
     *
     * @property itemView The View that this ViewHolder represents
     */
    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // UI components for displaying player information
        private val usernameTextView: TextView = itemView.findViewById(R.id.user_field)
        private val tapsTextView: TextView = itemView.findViewById(R.id.taps_field)
        private val placeTextView: TextView = itemView.findViewById(R.id.place_field)

        /**
         * Binds player data to the views at the specified position
         *
         * @param position The position of the player in the leaderboard
         */
        fun bind(position: Int) {
            players?.getOrNull(position)?.let { player ->
                // Set the player's tap count
                tapsTextView.text = "Taps: ${player.taps}"

                // Set the player's username
                usernameTextView.text = player.username

                // Set the player's position in the leaderboard (1-based indexing)
                placeTextView.text = "Place: ${position + 1}"
            }
        }
    }
}