package com.ayni.heroesatwork.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnTextChanged
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.launchActivity
import com.ayni.heroesatwork.views.adapters.HeroesAdapter
import java.util.*

class HeroSearchActivity : AppCompatActivity() {

    @BindView(R.id.heroes_recycler_view)
    lateinit var mHeroesRecyclerView: RecyclerView

    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hero_search_act)

        ButterKnife.bind(this)

        mHeroesRecyclerView.setHasFixedSize(true)
        mHeroesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mHeroesRecyclerView.adapter = HeroesAdapter(emptyList())


    }

    @OnTextChanged(value = R.id.hero_search_edit, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    fun afterHeroTextChanged() {
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {

            }
        }, 1000)
    }

    @OnTextChanged(value = R.id.hero_search_edit, callback = OnTextChanged.Callback.TEXT_CHANGED)
    fun onHeroTextChanged() {
        if (timer != null) {
            timer!!.cancel();
        }
    }

}