package com.ayni.heroesatwork.models

import java.util.*

class Game() {

    var gameId: Int = 0
    var organizationId: Int = 0
    var gameTemplateId: Int = 0
    lateinit var name: String
    lateinit var status: String
    var gameMasterId: Int = 0
    var gameVisibility: Int = 0
    lateinit var createdOn: Date
    lateinit var startedOn: Date
    lateinit var endedOn: Date
    lateinit var players: List<Player>
    lateinit var settings: List<Setting>
    lateinit var leaderBoard: LeaderBoard

    constructor(gameId: Int, name: String, status: String, players: List<Player>, settings: List<Setting>, leaderBoard: LeaderBoard): this() {
        this.gameId = gameId
        this.name = name
        this.status = status
        this.players = players
        this.settings = settings
        this.leaderBoard = leaderBoard
    }

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