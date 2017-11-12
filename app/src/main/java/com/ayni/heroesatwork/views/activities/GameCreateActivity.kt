package com.ayni.heroesatwork.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.launchActivity

class GameCreateActivity : AppCompatActivity() {

    @BindView(R.id.hero_add_button)
    lateinit var mHeroAddButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_create_act)

        ButterKnife.bind(this)


        mHeroAddButton.setOnClickListener(View.OnClickListener {
            view -> launchActivity<HeroSearchActivity> {  }
        })
    }
}