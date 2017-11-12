package com.ayni.heroesatwork.repositories

import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import com.ayni.heroesatwork.application.PreferenceHelper
import com.ayni.heroesatwork.di.DaggerDiComponent
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Member
import com.ayni.heroesatwork.repositories.MemberRepository
import com.ayni.heroesatwork.repositories.service.MemberService
import io.reactivex.Flowable
import javax.inject.Inject

class MemberRepositoryImpl() : MemberRepository {

    @Inject
    lateinit var memberService: MemberService

    override fun authenticate(email: String): Flowable<Member> {
        return memberService.authenticate(email)
    }

    init {
        DaggerDiComponent.builder().build().inject(this)
    }
}