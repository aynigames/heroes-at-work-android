package com.ayni.heroesatwork.views.activities

import android.os.Bundle
import android.view.Window
import butterknife.ButterKnife
import butterknife.OnClick
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.HeroesAtWorkActivity
import com.ayni.heroesatwork.application.launchActivity

class LoginActivity : HeroesAtWorkActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val sab = supportActionBar
        if (sab != null)
            sab!!.hide()

        setContentView(R.layout.login_act)

        ButterKnife.bind(this)
    }

    @OnClick(R.id.login_button)
    internal fun loginButtonClick() {
        launchActivity<GameListActivity>()
    }

    @OnClick(R.id.login_facebook_button)
    internal fun loginFacebookButtonClick() {
        launchActivity<GameListActivity>()
    }

}
