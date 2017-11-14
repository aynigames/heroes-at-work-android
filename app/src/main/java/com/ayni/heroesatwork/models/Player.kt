package com.ayni.heroesatwork.models

data class Player(
        var playerId: Int,
        var gameId: Int,
        var memberId: Int,
        var playerName: String,
        var playerLastName: String,
        var playerAvatar: String,
        var playerEmail: String,
        var playerScore: Float,
        var giverScore: Float) {

    fun playerFullName() : String {
        return playerName + " " + playerLastName
    }
}


