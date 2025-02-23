# This code will return a leaderboard from the leaderboard table
import sqlite3


def get_leaderboard():
    """
    Access top 10 values from leaderboard table
    """
    # Access the table
    conn = sqlite3.connect("leaderboad.db")
    cursor = conn.cursor()

    # Insert values into the table
    return cursor.execute("""SELECT TOP 10 * FROM leaderboard""").fetchall()
