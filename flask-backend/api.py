# This is a basic flask api to handle get and post requests
from flask import Flask, jsonify, make_response, request
from get_leaderboard import get_leaderboard_top_ten
from update_leaderboard import up_leaderboard

app = Flask(__name__)


@app.route("/get_leaderboard",methods =["GET"])
def get_leaderboard():
    """
    This route will focus on getting the current leaderboard
    """
    res, flag = get_leaderboard_top_ten()

    if flag:
        response = make_response(jsonify(res))
        response.headers['Content-Type'] = 'application/json'
        response.status_code = 200
        return response
    else:
        response = make_response(f"Error: {res}")
        response.headers['Content-Type'] = 'text/plain'
        response.status_code = 404
        return response


@app.route("/up_leaderboard", methods =["POST"])
def update_leaderboard():
    """
    This route will focus on updating the
    leaderboard with the players score
    """
    player = request.args.get("player")
    taps = request.args.get("taps")
    print(f"PLayer: {player} Taps: {taps}")
    flag, res = up_leaderboard(player, taps)

    if flag:
        response = make_response(res)
        response.headers['Content-Type'] = 'text/plain'
        response.status_code = 200
        return response
    else:
        print(res)
        response = make_response(f"Error: {res}")
        response.headers['Content-Type'] = 'text/plain'
        response.status_code = 404
        return response
