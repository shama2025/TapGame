# Will check to see if a username already exists
import sqlite3
from update_leaderboard import up_leaderboard


def check_username_exists(username):
    """
    This function checks if a username exists in the leaderboard database
    """
    try:
        # Connect to database
        conn = sqlite3.connect("leaderboad.db")
        cursor = conn.cursor()

        # Use parameterized query to prevent SQL injection
        cursor.execute(
            "SELECT username FROM leaderboard WHERE username = ?", (username,)
        )
        result = cursor.fetchone()

        # Check if username exists
        if result is None:
            """Then add the username to the database setting taps to 0"""
            flag, res = up_leaderboard(player=username, taps=0)
            return None,flag
        else:
            return "Username already exists!", False

    except sqlite3.Error as e:
        # Log the error and return appropriate message
        print(f"Database error: {str(e)}")
        return f"Database error: {str(e)}", False

    finally:
        # Ensure connection is always closed
        if "conn" in locals():
            conn.close()
