package com.ayni.heroesatwork.repositories

import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import com.ayni.heroesatwork.application.PreferenceHelper
import com.ayni.heroesatwork.di.DaggerDiComponent
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.repositories.service.GameService
import io.reactivex.Flowable
import javax.inject.Inject

class GameRepositoryImpl : GameRepository {

    @Inject
    lateinit var gameService: GameService

    override fun getCurrentGames(): Flowable<List<Game>> {
        val memberId = PreferenceHelper.getIntPreference(HeroesAtWorkConstants.MEMBER_ID_PREFERENCE_KEY)
        return gameService.getCurrentGames(memberId)
    }

    override fun getOldGames(): Flowable<List<Game>> {
        val memberId = PreferenceHelper.getIntPreference(HeroesAtWorkConstants.MEMBER_ID_PREFERENCE_KEY)
        return gameService.getOldGames(memberId)
    }

    init {
        DaggerDiComponent.builder().build().inject(this)
    }
}
