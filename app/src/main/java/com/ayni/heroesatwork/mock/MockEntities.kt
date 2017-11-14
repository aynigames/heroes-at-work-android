package com.ayni.heroesatwork.mock

import com.ayni.heroesatwork.application.DateUtils
import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import com.ayni.heroesatwork.models.*
import java.util.*

class MockEntities {
    companion object {
        val today = Date()
        val todayStart = DateUtils.getStartOfDay(today)
        var todayEnd = DateUtils.getEndOfDay(today)
        val setting1 = Setting(HeroesAtWorkConstants.SETTING_START_DATE, "2017-11-12T00:00:00")
        val setting2 = Setting(HeroesAtWorkConstants.SETTING_END_DATE, "2017-11-15T23:59:59")
        val settings = listOf(setting1, setting2)

        val dummyProfilePic = "http://www.tripurainfo.com/Matrimonials/Photos/DummyWoman.jpg"
        val player1 = Player(1, 1, 1, "Miguel", "Koo", dummyProfilePic, "miguel@aynilab.com", 120.0f, 20.0f)
        val player2 = Player(2, 1, 2, "Paolo", "Quinteros", dummyProfilePic, "paolo@aynilab.com", 150.0f, 20.0f)
        val player3 = Player(2, 1, 2, "Arturo", "Gamarra", dummyProfilePic, "arturo@aynilab.com", 20.0f, 20.0f)
        val players = listOf(player1, player2, player3)
        val leaderBoard = LeaderBoard(players)

        val game1 = Game(1, "Game 1", "In Progress", emptyList(), settings, leaderBoard)
        val game2 = Game(2, "Game 2", "In Progress", emptyList(), settings, leaderBoard)
        val game3 = Game(3, "Game 3", "In Progress", emptyList(), settings, leaderBoard)
        val game4 = Game(4, "Game 4", "In Progress", emptyList(), settings, leaderBoard)
        val member1 = Member(1, 1, "Miguel", "Koo", "miguel@aynilab.com", dummyProfilePic)
        val member2 = Member(2, 2, "Paolo", "Quinteros", "paolo@aynilab.com", dummyProfilePic)
        val member3 = Member(3, 3, "Arturo", "Gamarra", "arturo@aynilab.com", dummyProfilePic)

        val currentGames = mutableListOf<Game>(game1, game2)
        val oldGames = mutableListOf<Game>(game3, game4)
    }
}