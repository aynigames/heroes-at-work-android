package com.ayni.heroesatwork.di

import com.ayni.heroesatwork.mock.MockGameService
import com.ayni.heroesatwork.repositories.GameRepositoryImpl
import com.ayni.heroesatwork.repositories.MemberRepositoryImpl
import dagger.Component


@Component(modules = arrayOf(HeroesAtWorkModule::class))
interface DiComponent {
    fun inject(gameRepository: GameRepositoryImpl)
    fun inject(memberRepository: MemberRepositoryImpl)
}

