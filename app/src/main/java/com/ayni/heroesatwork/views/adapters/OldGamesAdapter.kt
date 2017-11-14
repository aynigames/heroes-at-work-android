package com.ayni.heroesatwork.views.adapters

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
import com.ayni.heroesatwork.application.PreferenceHelper
import com.ayni.heroesatwork.application.inflate
import com.ayni.heroesatwork.application.launchActivity
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.views.activities.GameDetailActivity
import java.text.SimpleDateFormat

class OldGamesAdapter (private var mGames: List<Game>): RecyclerView.Adapter<OldGamesAdapter.OldGameViewHolder>() {

    class OldGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.game_name_text)
        lateinit var nameTextView: TextView

        @BindView(R.id.game_from_text)
        lateinit var fromTextView: TextView

        @BindView(R.id.game_to_text)
        lateinit var toTextView: TextView

        @BindView(R.id.hero_my_points_text)
        lateinit var myPointsTextView: TextView

        @BindView(R.id.main_hero_image)
        lateinit var mainHeroImageView: ImageView

        @BindView(R.id.main_hero_name_text)
        lateinit var mainHeroNameTextView: TextView

        @BindView(R.id.main_hero_points_text)
        lateinit var mainHeroPointsTextView: TextView

        @BindView(R.id.main_hero_score_progress_bar)
        lateinit var mainHeroPointsProgressBar: RoundCornerProgressBar

        fun bind(game: Game) = with(itemView) {
            nameTextView.text = game.name

            val startDateSetting = game.getSetting(HeroesAtWorkConstants.SETTING_START_DATE)
            val startDate = SimpleDateFormat(HeroesAtWorkConstants.DATE_FORMAT).parse(startDateSetting!!.value)
            val endDateSetting = game.getSetting(HeroesAtWorkConstants.SETTING_END_DATE)
            val endDate = SimpleDateFormat(HeroesAtWorkConstants.DATE_FORMAT).parse(endDateSetting!!.value)

            val sdf = SimpleDateFormat("dd-MM-yyyy")

            fromTextView.text = sdf.format(startDate)
            toTextView.text = sdf.format(endDate)

            val memberId = PreferenceHelper.getIntPreference(HeroesAtWorkConstants.MEMBER_ID_PREFERENCE_KEY)
            val me = game.getPlayer(memberId)
            myPointsTextView.text = me!!.playerScore.toString()

            //TODO: Main Hero Face
            mainHeroImageView.setImageResource(R.mipmap.ic_launcher_round)

            val mainHero = game.getTopPlayer()
            mainHeroPointsTextView.text = mainHero!!.playerScore.toString()
            mainHeroNameTextView.text = mainHero.playerFullName()

            //TODO: Main Hero Progress
            mainHeroPointsProgressBar.max = 100.0f
            mainHeroPointsProgressBar.progress = 50.0f

            //TODO: Heroes Faces


            setOnClickListener { View.OnClickListener { context.launchActivity<GameDetailActivity> { } } }
        }

        init {
            ButterKnife.bind(this, itemView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            OldGameViewHolder(parent.inflate(R.layout.old_game_view))

    override fun onBindViewHolder(holder: OldGameViewHolder, position: Int) {
        holder.bind(mGames[position])
    }

    override fun getItemCount(): Int = mGames.size


    fun swap(newData: List<Game>) {
        mGames = newData
        notifyDataSetChanged()
    }

}