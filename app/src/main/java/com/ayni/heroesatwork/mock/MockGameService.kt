package com.ayni.heroesatwork.mock

import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Member
import com.ayni.heroesatwork.repositories.service.GameService
import io.reactivex.Flowable

class MockGameService : GameService {


    override fun getCurrentGames(memberId: Int): Flowable<List<Game>> {
        return Flowable.fromArray(MockEntities.currentGames)
    }

    override fun getOldGames(memberId: Int): Flowable<List<Game>> {
        return Flowable.fromArray(MockEntities.oldGames)
    }

    override fun saveGame(game: Game): Flowable<Game> {
        MockEntities.currentGames.add(game)
        return Flowable.just(game)
    }

    override fun getMyPointsDetails(gameId: Int, memberId: Int): Flowable<List<Member>> {
        return Flowable.fromArray(listOf(MockEntities.member1, MockEntities.member2, MockEntities.member3))
    }

    override fun searchMembers(text : String): Flowable<List<Member>> {
        return Flowable.fromArray(listOf(MockEntities.member1, MockEntities.member2, MockEntities.member3))
    }

    override fun vote(gameId: Int): Flowable<List<Member>> {
        return Flowable.fromArray(listOf(MockEntities.member1, MockEntities.member2, MockEntities.member3))
    }
}