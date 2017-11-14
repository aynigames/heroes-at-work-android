package com.ayni.heroesatwork.mock

import com.ayni.heroesatwork.models.Member
import com.ayni.heroesatwork.models.Player
import com.ayni.heroesatwork.repositories.service.MemberService
import io.reactivex.Flowable

class MockMemberService : MemberService {
    override fun authenticate(email: String): Flowable<Member> {
        return Flowable.just(MockEntities.member1)
    }

    override fun searchPlayers(name: String): Flowable<List<Player>> {
        return Flowable.fromArray(MockEntities.players)
    }
}
