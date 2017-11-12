package com.ayni.heroesatwork.viewmodels

import android.arch.lifecycle.ViewModel
import com.ayni.heroesatwork.models.Member
import com.ayni.heroesatwork.repositories.MemberRepository
import com.ayni.heroesatwork.repositories.MemberRepositoryImpl
import io.reactivex.Flowable

class MemberViewModel : ViewModel() {

    val memberRepository : MemberRepository = MemberRepositoryImpl()

    fun authenticate(email: String): Flowable<Member> {
        return memberRepository.authenticate(email)
    }
}