package com.ayni.heroesatwork.repositories.service

import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Member
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface GameService {
    @GET("games?status=start")
    fun getCurrentGames(@Query("memberid")memberid: Int): Flowable<List<Game>>

    @GET("games?status=end")
    fun getOldGames(@Query("memberid")memberid: Int): Flowable<List<Game>>

    @POST("games")
    fun saveGame(game: Game): Flowable<Game>

    @GET("members")
    fun searchMembers(@Query("text")text : String): Flowable<List<Member>>

    @POST("games/{gameId}/members/{memberId}")
    fun getMyPointsDetails(@Path("gameId") gameId: Int, @Path("memberId") memberId: Int): Flowable<List<Member>>

    @POST("games/{gameId}/points")
    fun vote(@Path("gameId") gameId: Int): Flowable<List<Member>>

}