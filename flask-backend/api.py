# This will feature a flask backend to support user storage
from flask import Flask
from get_leaderboard import get_leaderboard
app = Flask(__name__)

@app.route("/get_leaderboard")
def get_leaderboard():
    """
    This route will focus on getting the current leaderboard
    """
    
    return

@app.route("/up_leaderboard")
def update_leaderboard():
    """
    This route will focus on updating the
    leaderboard with the players score
    """
    return ""