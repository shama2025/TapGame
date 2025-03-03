# This code will be used as a way to test the backend prior to integration
import requests
import names


def test_get_leaderboard_top_ten():
    response = requests.get("http://127.0.0.1:5000/get_leaderboard")
    if response.status_code == 200:
        assert True
    else:
        assert False


def test_update_leaderboard():
    player = {"name": "Player11", "taps": 465}
    response = requests.put(
        f"http://127.0.0.1:5000/up_leaderboard?player={player['name']}&taps={player['taps']}"
    )
    if response.status_code == 200:
        assert True
    else:
        assert False


def test_username_does_not_exist():
    player = {"name": names.get_first_name()}
    response = requests.get(
        f"http://127.0.0.1:5000/username_exists?username={player['name']}"
    )
    if response.status_code == 200:
        assert True
    else:
        assert False


def test_username_does_exist():
    player = {"name": "Player1"}
    response = requests.get(
        f"http://127.0.0.1:5000/username_exists?username={player['name']}"
    )
    if response.status_code == 404:
        assert True
    else:
        assert False
