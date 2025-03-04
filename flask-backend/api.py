# This is a basic flask api to handle get and post requests
from flask import Flask, Response, request, json
from get_leaderboard import get_leaderboard_top_ten
from update_leaderboard import up_leaderboard
from format_data import format_data
from check_username import check_username_exists

app = Flask(__name__)


@app.route("/get_leaderboard", methods=["GET"])
def get_leaderboard():
    """
    This route will focus on getting the current leaderboard
    """
    res, flag = get_leaderboard_top_ten()

    if flag:
        return Response(
            json.dumps(format_data(res)), status=200, content_type="application/json"
        )
    else:
        return Response(
            json.dumps({"error": "Error"}), status=404, content_type="application/json"
        )


@app.route("/up_leaderboard", methods=["PUT"])
def update_leaderboard():
    """
    This route will focus on updating the
    leaderboard with the players score
    """
    username = request.args.get("username")
    taps = request.args.get("taps")
    flag, res = up_leaderboard(username, taps)

    if flag:
        return Response(json.dumps(res), 200, content_type="text/plain")

    else:
        return Response(json.dumps(f"Error: {res}"), 404, content_type="text/plain")


@app.route("/username_exists", methods=["GET"])
def check_username():
    """
    This route is used to check if a username already exists
    when a user creates a new player
    """
    username = request.args.get("username")

    res, flag = check_username_exists(username)

    if flag and res is None:
        return Response(
            json.dumps("Username was added"), 200, content_type="text/plain"
        )
    elif not flag  and res != None:
        return Response(
            json.dumps("Username exists"), 400, content_type="text/plain"
        )
    else:
        return Response(
            json.dumps("Error Adding username"), 404, content_type="text/plain"
        )
