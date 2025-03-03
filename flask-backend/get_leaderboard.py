# This code will get the top 10 players from the leaderboard
import sqlite3


def get_leaderboard_top_ten():
    """
    Access top 10 values from leaderboard table
    """
    try:
        # Access the table
        conn = sqlite3.connect("leaderboad.db")
        cursor = conn.cursor()

        # Retrieve top 10 values from table

        res = cursor.execute(
            """SELECT * FROM leaderboard ORDER BY taps DESC LIMIT 10"""
        ).fetchall()
        return res, True
    except sqlite3.Error as e:
        print(f"{str(e)}")
        return str(e), False
    finally:
        if "conn" in locals():
            conn.close()
