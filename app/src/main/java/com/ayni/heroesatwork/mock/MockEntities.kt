package com.ayni.heroesatwork.mock

import com.ayni.heroesatwork.application.DateUtils
import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import com.ayni.heroesatwork.models.*
import java.util.*

class MockEntities {
    companion object {
        private val today = Date()
        private val todayStart = DateUtils.getStartOfDay(today)
        var todayEnd = DateUtils.getEndOfDay(today)
        private val setting1 = Setting(HeroesAtWorkConstants.SETTING_START_DATE, "2017-11-12T00:00:00")
        private val setting2 = Setting(HeroesAtWorkConstants.SETTING_END_DATE, "2017-11-18T23:59:59")
        private val setting3 = Setting(HeroesAtWorkConstants.SETTING_POINTS_PER_HERO, "200")
        private val settings = mutableListOf(setting1, setting2, setting3)

        private val dummyProfilePic = "http://www.tripurainfo.com/Matrimonials/Photos/DummyWoman.jpg"
        private val player1 = Player(1, 1, 1, "Miguel", "Koo", dummyProfilePic, "miguel@aynilab.com", 120.0f, 20.0f)
        private val player2 = Player(2, 1, 2, "Paolo", "Quinteros", dummyProfilePic, "paolo@aynilab.com", 150.0f, 20.0f)
        private val player3 = Player(3, 1, 3, "Arturo", "Gamarra", dummyProfilePic, "arturo@aynilab.com", 20.0f, 20.0f)
        val players = listOf(player1, player2, player3)
        private val leaderBoard = LeaderBoard(players)

        private val game1 = Game(1, "Game 1", "In Progress", emptyList(), settings, leaderBoard)
        private val game2 = Game(2, "Game 2", "In Progress", emptyList(), settings, leaderBoard)
        private val game3 = Game(3, "Game 3", "In Progress", emptyList(), settings, leaderBoard)
        private val game4 = Game(4, "Game 4", "In Progress", emptyList(), settings, leaderBoard)
        val member1 = Member(1, 1, "Miguel", "Koo", "miguel@aynilab.com", dummyProfilePic)
        val member2 = Member(2, 2, "Paolo", "Quinteros", "paolo@aynilab.com", dummyProfilePic)
        val member3 = Member(3, 3, "Arturo", "Gamarra", "arturo@aynilab.com", dummyProfilePic)

        val currentGames = mutableListOf(game1, game2)
        val oldGames = mutableListOf(game3, game4)
    }
}