package com.ayni.heroesatwork.repositories

import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Member
import io.reactivex.Flowable

interface MemberRepository {
    fun authenticate(email: String): Flowable<Member>
}
