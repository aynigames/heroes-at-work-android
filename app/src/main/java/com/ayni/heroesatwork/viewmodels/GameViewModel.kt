package com.ayni.heroesatwork.viewmodels

import android.arch.lifecycle.ViewModel
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.repositories.GameRepository
import com.ayni.heroesatwork.repositories.GameRepositoryImpl
import io.reactivex.Flowable

class GameViewModel : ViewModel() {

    private val gameRepository : GameRepository = GameRepositoryImpl()

    fun getCurrentGames(): Flowable<List<Game>> {
        return gameRepository.getCurrentGames()
    }

    fun getOldGames(): Flowable<List<Game>> {
        return gameRepository.getOldGames()
    }
}