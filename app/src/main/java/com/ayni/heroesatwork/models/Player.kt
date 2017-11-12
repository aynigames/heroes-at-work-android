package com.ayni.heroesatwork.models

import java.util.*

data class Player(
        var PlayerId: Int,
        var GameId: Int,
        var MemberId: Int,
        var PlayerName: String,
        var PlayerLastName: String,
        var PlayerAvatar: String,
        var PlayerEmail: String,
        var Players: List<Player>)