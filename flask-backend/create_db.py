# This file will be used to create a leaderboard database using sqlite3
import sqlite3

# Connect to a database
conn = sqlite3.connect("leaderboad.db")
cursor = conn.cursor()

# Create a table
cursor.execute("CREATE TABLE leaderboard(place INTEGER PRIMARY KEY, username TEXT, taps INTEGER)")

# Insert values into the table
cursor.execute("""
INSERT INTO leaderboard VALUES
               (1, 'Player 1', 500),
               (2, 'Player 2', 475),
               (3, 'Player 3', 450),
               (4, 'Player 4', 430),
               (5, 'Player 5', 410),
               (6, 'Player 6', 390),
               (7, 'Player 7', 370),
               (8, 'Player 8', 350),
               (9, 'Player 9', 330),
               (10, 'Player 10', 310)
""")

# Update table
conn.commit()