package com.ayni.heroesatwork.repositories.service

import com.ayni.heroesatwork.models.Game
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface GameService {
    @GET("api/games")
    fun getCurrentGames(): Flowable<List<Game>>

    @GET("api/games")
    fun getOldGames(): Flowable<List<Game>>

    @POST("api/games")
    fun saveGame(game: Game): Flowable<Game>

}