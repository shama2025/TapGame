# This file will format the data from sqlite3 to make it ready for requests
from collections import namedtuple


def format_data(players):
    """
    This function will format the data into json objects
    """
    Player = namedtuple("Player", ["place", "name", "taps"])

    data = [
        Player(place=player[0], name=player[1], taps=player[2])._asdict()
        for player in players
    ]

    return data
