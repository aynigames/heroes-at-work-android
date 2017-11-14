package com.ayni.heroesatwork.repositories.service

import com.ayni.heroesatwork.models.Member
import com.ayni.heroesatwork.models.Player
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MemberService {
    @GET("members")
    fun authenticate(@Query("email") email: String): Flowable<Member>

    @GET("players")
    fun searchPlayers(@Query("text") name: String): Flowable<List<Player>>
}