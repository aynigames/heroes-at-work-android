package com.ayni.heroesatwork.repositories

import com.ayni.heroesatwork.di.DaggerDiComponent
import com.ayni.heroesatwork.models.Member
import com.ayni.heroesatwork.models.Player
import com.ayni.heroesatwork.repositories.service.MemberService
import io.reactivex.Flowable
import javax.inject.Inject

class MemberRepositoryImpl : MemberRepository {

    @Inject
    lateinit var memberService: MemberService

    override fun authenticate(email: String): Flowable<Member> {
        return memberService.authenticate(email)
    }

    override fun searchPlayers(name: String): Flowable<List<Player>> {
        return memberService.searchPlayers(name)
    }

    init {
        DaggerDiComponent.builder().build().inject(this)
    }
}