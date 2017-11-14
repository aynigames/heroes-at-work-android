package com.ayni.heroesatwork.views.listeners

import com.ayni.heroesatwork.models.Player

interface OnHeroDeletedListener {
    fun onHeroDeleted(hero: Player)
}