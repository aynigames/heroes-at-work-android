package com.ayni.heroesatwork.repositories.service

import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Member
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MemberService {
    @GET("members")
    fun authenticate(@Query("email") email: String): Flowable<Member>
}