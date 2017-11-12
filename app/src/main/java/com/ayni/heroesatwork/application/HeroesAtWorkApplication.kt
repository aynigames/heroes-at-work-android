package com.ayni.heroesatwork.application

import android.app.Application

class HeroesAtWorkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: HeroesAtWorkApplication? = null
    }
}