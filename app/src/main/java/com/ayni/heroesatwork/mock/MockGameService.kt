package com.ayni.heroesatwork.mock

import com.ayni.heroesatwork.application.DateUtils
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.repositories.service.GameService
import io.reactivex.Flowable
import retrofit2.Call
import java.util.Date


class MockGameService() : GameService {
    companion object {
        val today = Date()
        val todayStart = DateUtils.getStartOfDay(today)
        var todayEnd = DateUtils.getEndOfDay(today)


        val game1 = Game(1, 1, 1,"Game 1", "In Progress", 1, 1, todayStart, todayStart, todayEnd, emptyList())
        val game2 = Game(2, 1, 1,"Game 2", "In Progress", 1, 1, todayStart, todayStart, todayEnd, emptyList())
        val game3 = Game(3, 1, 1,"Game 3", "In Progress", 1, 1, todayStart, todayStart, todayEnd, emptyList())
        val game4 = Game(4, 1, 1,"Game 4", "In Progress", 1, 1, todayStart, todayStart, todayEnd, emptyList())

        val currentGames = mutableListOf<Game>(game1, game2)
        val oldGames = mutableListOf<Game>(game3, game4)
    }

    override fun getCurrentGames(): Flowable<List<Game>> {
        return Flowable.fromArray(currentGames)
    }

    override fun getOldGames(): Flowable<List<Game>> {
        return Flowable.fromArray(oldGames)
    }

    override fun saveGame(game: Game): Flowable<Game> {
        currentGames.add(game)
        return Flowable.just(game)
    }
}