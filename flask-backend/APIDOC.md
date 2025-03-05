# API Documentation

## Overview
This API provides endpoints to retrieve and update a leaderboard for a game, as well as check if a username already exists.

## Base URL
```
http://<your-server-address>/
```

## Endpoints

### 1. Get Leaderboard
**Endpoint:**
```
GET /get_leaderboard
```
**Description:**
Retrieves the top ten players from the leaderboard.

**Response:**
- **200 OK:** Returns a JSON list of the top ten players.
- **404 Not Found:** Returns an error message if the leaderboard data could not be retrieved.

**Example Response:**
```json
[
    {"username": "player1", "score": 1500},
    {"username": "player2", "score": 1200}
]
```

---

### 2. Update Leaderboard
**Endpoint:**
```
PUT /up_leaderboard?username=<username>&taps=<score>
```
**Description:**
Updates the leaderboard with a player's score.

**Query Parameters:**
- `username` (string, required) – The username of the player.
- `taps` (integer, required) – The score to be recorded.

**Response:**
- **200 OK:** Successfully updated the leaderboard.
- **404 Not Found:** Returns an error message if the update failed.

**Example Request:**
```
PUT /up_leaderboard?username=player1&taps=2000
```
**Example Response:**
```json
"Leaderboard updated successfully"
```

---

### 3. Check Username Existence
**Endpoint:**
```
GET /username_exists?username=<username>
```
**Description:**
Checks whether a username already exists.

**Query Parameters:**
- `username` (string, required) – The username to check.

**Response:**
- **200 OK:** Username was successfully added.
- **400 Bad Request:** Username already exists.
- **404 Not Found:** Error occurred while adding the username.

**Example Request:**
```
GET /username_exists?username=player1
```
**Example Response:**
```json
"Username exists"
```

