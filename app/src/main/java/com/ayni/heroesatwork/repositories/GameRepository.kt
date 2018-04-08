package com.ayni.heroesatwork.repositories

import com.ayni.heroesatwork.models.Game
import io.reactivex.Flowable

interface GameRepository {
    fun getCurrentGames(): Flowable<List<Game>>
    fun getOldGames(): Flowable<List<Game>>
    fun createGame(game: Game): Flowable<Game>
}


