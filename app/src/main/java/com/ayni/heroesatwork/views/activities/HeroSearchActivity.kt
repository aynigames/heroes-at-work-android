package com.ayni.heroesatwork.views.activities

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnTextChanged
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import com.ayni.heroesatwork.models.Player
import com.ayni.heroesatwork.viewmodels.MemberViewModel
import com.ayni.heroesatwork.views.adapters.HeroesSearchAdapter
import com.ayni.heroesatwork.views.listeners.OnHeroSelectedListener
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class HeroSearchActivity : AppCompatActivity(), OnHeroSelectedListener {

    @BindView(R.id.hero_search_edit)
    lateinit var mHeroSearchEditText : EditText

    @BindView(R.id.heroes_recycler_view)
    lateinit var mHeroesRecyclerView: RecyclerView

    private var timer: Timer? = null

    lateinit var memberViewModel : MemberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hero_search_act)

        ButterKnife.bind(this)

        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel::class.java)

        mHeroesRecyclerView.setHasFixedSize(true)
        mHeroesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mHeroesRecyclerView.adapter = HeroesSearchAdapter(emptyList(), null, this@HeroSearchActivity)
    }

    @OnTextChanged(value = R.id.hero_search_edit, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    fun afterHeroTextChanged() {
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                val text = mHeroSearchEditText.text.toString()
                memberViewModel.searchPlayers(text).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { heroes -> (mHeroesRecyclerView.adapter as HeroesSearchAdapter).swap(heroes) },
                                { _ -> Toast.makeText(this@HeroSearchActivity, "There was an error obtaining the players.", Toast.LENGTH_LONG).show() }
                        )
            }
        }, 1000)
    }

    @OnTextChanged(value = R.id.hero_search_edit, callback = OnTextChanged.Callback.TEXT_CHANGED)
    fun onHeroTextChanged() {
        if (timer != null) {
            timer!!.cancel()
        }
    }

    override fun onHeroSelected(hero: Player) {
        val intent = Intent()
        val bundle = Bundle()
        val heroJson = Gson().toJson(hero)
        bundle.putString(HeroesAtWorkConstants.HERO_SELECTED_BUNDLE_KEY, heroJson)
        intent.putExtra(HeroesAtWorkConstants.HERO_SELECTED_BUNDLE_KEY, bundle)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}