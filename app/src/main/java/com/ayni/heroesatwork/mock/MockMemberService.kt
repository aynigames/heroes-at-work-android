package com.ayni.heroesatwork.mock

import com.ayni.heroesatwork.application.DateUtils
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Member
import com.ayni.heroesatwork.repositories.service.GameService
import com.ayni.heroesatwork.repositories.service.MemberService
import io.reactivex.Flowable
import java.util.*

class MockMemberService() : MemberService {
    override fun authenticate(email: String): Flowable<Member> {
        return Flowable.just(MockEntities.member1)
    }
}
