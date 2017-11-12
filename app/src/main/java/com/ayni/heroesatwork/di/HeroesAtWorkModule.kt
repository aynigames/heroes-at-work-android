package com.ayni.heroesatwork.di

import com.ayni.heroesatwork.application.HeroesAtWorkRetrofit
import com.ayni.heroesatwork.mock.MockGameService
import com.ayni.heroesatwork.repositories.service.GameService
import dagger.Module
import dagger.Provides

/*
@Module
class HeroesAtWorkModule {
    @Provides
    fun provideGameService() : GameService {
        return HeroesAtWorkRetrofit.instance.create(GameService::class.java)
    }
}
*/
@Module
class HeroesAtWorkModule {
    @Provides
    fun provideGameService() : GameService {
        return MockGameService()
    }


}