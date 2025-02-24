# This code will be used as a way to test the backend prior to integration
import requests

def test_get_leaderboard_top_ten():
    response = requests.get("http://127.0.0.1:5000/get_leaderboard")
    if response.status_code == 200:
        assert True
    else:
        assert False

def test_update_leaderboard():
    player = {
        "name" : "Player11",
        "taps" : 465
    }
    response = requests.post(f"http://127.0.0.1:5000/up_leaderboard?player={player['name']}&taps={player['taps']}")
    if response.status_code == 200:
        assert True
    else:
        assert False