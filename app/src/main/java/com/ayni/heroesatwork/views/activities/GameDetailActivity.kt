package com.ayni.heroesatwork.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.launchActivity

class GameDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_detail_act)

        ButterKnife.bind(this)

    }

    @OnClick(R.id.current_score_view)
    internal fun CurrentScoreViewClick() {
        launchActivity<GamePointsActivity>()
    }
}