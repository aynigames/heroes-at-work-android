package com.ayni.heroesatwork.repositories

import com.ayni.heroesatwork.application.HeroesAtWorkRetrofit
import com.ayni.heroesatwork.di.DaggerDiComponent
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.repositories.service.GameService
import io.reactivex.Flowable
import javax.inject.Inject

class GameRepositoryImpl : GameRepository {



    constructor() {
        DaggerDiComponent.builder().build().inject(this)
    }

    @Inject
    lateinit var gameService: GameService

    override fun getCurrentGames(): Flowable<List<Game>> {
        return gameService.getCurrentGames()
    }

    override fun getOldGames(): Flowable<List<Game>> {
        return gameService.getOldGames()
    }
}
