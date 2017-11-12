package com.ayni.heroesatwork.models

import java.util.*

data class Game(
        var gameId: Int,
        var organizationId: Int,
        var gameTemplateId: Int,
        var name: String,
        var status: String,
        var gameMasterId: Int,
        var gameVisibility: Int,
        var createdOn: Date,
        var startedOn: Date,
        var endedOn: Date,
        var players: List<Player>,
        var settings: List<Setting>,
        var leaderBoard: LeaderBoard) {

    fun getSetting(key: String): Setting? {
        return settings.find { setting -> setting.key == key }
    }

    fun getPlayer(memberId: Int): Player? {
        return leaderBoard.players.find { player -> player.memberId == memberId }
    }

    fun getTopPlayer(): Player? {
        return leaderBoard.players.maxBy { player -> player.playerScore }
    }
}