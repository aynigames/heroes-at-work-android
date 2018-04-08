package com.ayni.heroesatwork.views.adapters

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import com.ayni.heroesatwork.application.inflate
import com.ayni.heroesatwork.application.launchActivity
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Player
import com.ayni.heroesatwork.views.activities.GameVoteActivity
import com.google.gson.Gson

class HeroesAdapter(private var mHeroes: List<Player>, private var mGame: Game): RecyclerView.Adapter<HeroesAdapter.HeroViewHolder>() {

    class HeroViewHolder(var mGame: Game, itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.hero_name_text)
        lateinit var mHeroNameTextView : TextView

        @BindView(R.id.hero_image)
        lateinit var mHeroImageView : ImageView

        @BindView(R.id.hero_points_text)
        lateinit var mHeroPointsTextView : TextView

        @BindView(R.id.hero_score_progress_bar)
        lateinit var mHeroScoreProgressBar : RoundCornerProgressBar

        @BindView(R.id.hero_vote_button)
        lateinit var mHeroVoteButton : ImageView

        private lateinit var mHero : Player

        fun bind(hero: Player) = with(itemView) {
            mHero = hero
            mHeroNameTextView.text = hero.playerFullName()
            mHeroPointsTextView.text = "%.0f".format(hero.playerScore)
            //TODO: Set Score
            mHeroScoreProgressBar.progress = hero.playerScore
            mHeroScoreProgressBar.max = mGame.getMaxScore()!!

            //TODO: Set image
            mHeroImageView.setImageResource(R.mipmap.ic_launcher_round)

            mHeroVoteButton.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(HeroesAtWorkConstants.GAME_SELECTED_BUNDLE_KEY, Gson().toJson(mGame))
                bundle.putString(HeroesAtWorkConstants.HERO_SELECTED_BUNDLE_KEY, Gson().toJson(mHero))
                context.launchActivity<GameVoteActivity>(options = bundle)
            }
        }


        init {
            ButterKnife.bind(this, itemView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HeroViewHolder(mGame, parent.inflate(R.layout.hero_current_view))

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(mHeroes[position])
    }

    override fun getItemCount(): Int = mHeroes.size


    fun swap(newData: List<Player>) {
        mHeroes = newData
        notifyDataSetChanged()
    }

}