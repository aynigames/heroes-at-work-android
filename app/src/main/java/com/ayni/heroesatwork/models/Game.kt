package com.ayni.heroesatwork.models

import java.util.*

class Game() {

    private var gameId: Int = 0
    var organizationId: Int = 0
    var gameTemplateId: Int = 0
    lateinit var name: String
    private lateinit var status: String
    var gameMasterId: Int = 0
    var gameVisibility: Int = 0
    lateinit var createdOn: Date
    lateinit var startedOn: Date
    lateinit var endedOn: Date
    private lateinit var players: List<Player>
    private var settings = mutableListOf<Setting>()
    lateinit var leaderBoard: LeaderBoard

    constructor(gameId: Int, name: String, status: String, players: List<Player>, settings: MutableList<Setting>, leaderBoard: LeaderBoard): this() {
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

    fun putSetting(key: String, value: String) {
        var setting  = settings.find { s -> s.key == key }
        if (setting == null) {
            settings.add(Setting(key, value))
        }
        else {
            setting.value = value
        }
    }

    fun getMaxScore() : Float? {
        return leaderBoard.players.map { p -> p.playerScore }.max()
    }
}