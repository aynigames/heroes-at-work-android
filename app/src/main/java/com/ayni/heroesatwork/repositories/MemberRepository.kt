package com.ayni.heroesatwork.repositories

import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Member
import com.ayni.heroesatwork.models.Player
import io.reactivex.Flowable

interface MemberRepository {
    fun authenticate(email: String): Flowable<Member>
    fun searchPlayers(name: String): Flowable<List<Player>>
}
