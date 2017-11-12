package com.ayni.heroesatwork.di

import com.ayni.heroesatwork.application.HeroesAtWorkRetrofit
import com.ayni.heroesatwork.mock.MockGameService
import com.ayni.heroesatwork.mock.MockMemberService
import com.ayni.heroesatwork.repositories.service.GameService
import com.ayni.heroesatwork.repositories.service.MemberService
import dagger.Module
import dagger.Provides

class

open class HeroesAtWorkModuleProd {
    fun provideGameService() : GameService {
        return HeroesAtWorkRetrofit.instance.create(GameService::class.java)
    }
    fun provideMemberService(): MemberService {
        return HeroesAtWorkRetrofit.instance.create(MemberService::class.java)
    }
}


open class HeroesAtWorkModuleDev {
    fun provideGameService() : GameService {
        return MockGameService()
    }
    fun provideMemberService() : MemberService {
        return MockMemberService()
    }
}

@Module
class HeroesAtWorkModule() {

    companion object {
        val module = HeroesAtWorkModuleDev()
    }

    @Provides
    fun provideGameService() : GameService {
        return module.provideGameService()
    }

    @Provides
    fun provideMemberService() : MemberService {
        return module.provideMemberService()
    }
}