# This code will use a player object to update the leaderboard table
import sqlite3

def up_leaderboard(player,taps):
     # Access the table
    conn = sqlite3.connect("leaderboad.db")
    cursor = conn.cursor()

    # Insert values into the table
    try:
        cursor.execute(f"""INSERT INTO leaderboard (username, taps) VALUES (? , ?)""", (player, taps))
        conn.commit()
        return True, f"{player} has been added to the leaderboard!"
    except sqlite3.Error as e:
        return False, f"{str(e)}"
