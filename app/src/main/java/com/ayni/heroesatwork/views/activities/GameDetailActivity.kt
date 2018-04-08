package com.ayni.heroesatwork.views.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.DateUtils
import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import com.ayni.heroesatwork.application.PreferenceHelper
import com.ayni.heroesatwork.application.launchActivity
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Player
import com.ayni.heroesatwork.views.adapters.HeroesAdapter
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class GameDetailActivity : AppCompatActivity() {

    @BindView(R.id.heroes_recycler_view)
    lateinit var mHeroesRecyclerView: RecyclerView

    @BindView(R.id.remaining_text)
    lateinit var mRemainingText : TextView

    @BindView(R.id.current_score_text)
    lateinit var mCurrentScoreText : TextView

    @BindView(R.id.days_left_text)
    lateinit var mDaysLeftText : TextView

    @BindView(R.id.days_left_subtitle)
    lateinit var mDaysLeftSubtitle :  TextView


    private lateinit var mGame : Game
    private lateinit var mMe : Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_detail_act)

        ButterKnife.bind(this)

        val gameJson = intent.getStringExtra(HeroesAtWorkConstants.GAME_SELECTED_BUNDLE_KEY)
        mGame = Gson().fromJson(gameJson, Game::class.java)

        setTitle(mGame.name)

        val myMemberId = PreferenceHelper.getIntPreference(HeroesAtWorkConstants.MEMBER_ID_PREFERENCE_KEY)
        mMe = mGame.getPlayer(myMemberId)!!

        val pointsPerHero = mGame.getSetting(HeroesAtWorkConstants.SETTING_POINTS_PER_HERO)!!.value.toFloat()

        mRemainingText.text = "%.0f".format(pointsPerHero - mMe.giverScore)
        mCurrentScoreText.text = "%.0f".format(mMe.playerScore)

        val today = DateUtils.getStartOfDay(Date())
        val endDateSetting = mGame.getSetting(HeroesAtWorkConstants.SETTING_END_DATE)
        val endDate = SimpleDateFormat(HeroesAtWorkConstants.DATE_FORMAT).parse(endDateSetting!!.value)
        val diff = (endDate.time - today.time) / (24 * 60 * 60 * 1000)

        mDaysLeftText.text = diff.toString()
        mDaysLeftSubtitle.text = if (diff == 1L)"day left" else "days left"

        mHeroesRecyclerView.setHasFixedSize(true)
        mHeroesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mHeroesRecyclerView.adapter = HeroesAdapter(mGame.leaderBoard.players, mGame)

    }

    @OnClick(R.id.current_score_view)
    internal fun onCurrentScoreViewClick() {
        launchActivity<GamePointsActivity>()
    }
}