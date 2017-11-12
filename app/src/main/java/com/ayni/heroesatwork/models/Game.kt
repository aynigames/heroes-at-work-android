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
        var players: List<Player>)