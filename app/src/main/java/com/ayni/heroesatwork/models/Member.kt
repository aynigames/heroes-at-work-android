package com.ayni.heroesatwork.models

data class Member(
        var memberId: Int,
        var friendId: Int,
        var firstName: String,
        var lastName: String,
        var email: String,
        var profileImageUrl: String)
