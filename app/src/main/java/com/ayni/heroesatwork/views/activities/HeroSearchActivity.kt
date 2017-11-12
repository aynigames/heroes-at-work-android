package com.ayni.heroesatwork.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.launchActivity

class HeroSearchActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hero_search_act)

        ButterKnife.bind(this)


    }
}