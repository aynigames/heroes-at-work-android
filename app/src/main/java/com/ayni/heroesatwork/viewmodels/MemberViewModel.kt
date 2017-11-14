package com.ayni.heroesatwork.viewmodels

import android.arch.lifecycle.ViewModel
import com.ayni.heroesatwork.models.Member
import com.ayni.heroesatwork.models.Player
import com.ayni.heroesatwork.repositories.MemberRepository
import com.ayni.heroesatwork.repositories.MemberRepositoryImpl
import io.reactivex.Flowable

class MemberViewModel : ViewModel() {

    private val memberRepository : MemberRepository = MemberRepositoryImpl()

    fun authenticate(email: String): Flowable<Member> {
        return memberRepository.authenticate(email)
    }

    fun searchPlayers(name: String): Flowable<List<Player>> {
        return memberRepository.searchPlayers(name)
    }
}