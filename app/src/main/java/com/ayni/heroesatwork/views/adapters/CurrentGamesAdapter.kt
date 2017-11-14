package com.ayni.heroesatwork.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.DateUtils
import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import com.ayni.heroesatwork.application.inflate
import com.ayni.heroesatwork.application.launchActivity
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.views.activities.GameDetailActivity
import java.text.SimpleDateFormat
import java.util.*

class CurrentGamesAdapter (private var mGames: List<Game>): RecyclerView.Adapter<CurrentGamesAdapter.CurrentGameViewHolder>() {

    class CurrentGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView((R.id.game_name_text))
        lateinit var nameTextView: TextView

        @BindView(R.id.game_remaining_days_text)
        lateinit var remainingDaysTextView: TextView

        fun bind(game: Game) = with(itemView) {
            nameTextView.text = game.name

            val today = DateUtils.getStartOfDay(Date())
            val endDateSetting = game.getSetting(HeroesAtWorkConstants.SETTING_END_DATE)
            val endDate = SimpleDateFormat(HeroesAtWorkConstants.DATE_FORMAT).parse(endDateSetting!!.value)

            val diff = (endDate.time - today.time) / (24 * 60 * 60 * 1000)
            when {
                diff < 0 -> remainingDaysTextView.text = context.getString(R.string.just_finished)
                diff == 0L -> remainingDaysTextView.text = context.getString(R.string.finishes_today)
                diff == 1L -> remainingDaysTextView.text = context.getString(R.string.finishes_tomorrow)
                else -> remainingDaysTextView.text = String.format(context.getString(R.string.finishes_in_x_days), diff)
            }

            //TODO: Heroes faces


            itemView.setOnClickListener { context.launchActivity<GameDetailActivity> {  } }
        }

        init {
            ButterKnife.bind(this, itemView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CurrentGameViewHolder(parent.inflate(R.layout.current_game_view))

    override fun onBindViewHolder(holder: CurrentGameViewHolder, position: Int) {
        holder.bind(mGames[position])
    }

    override fun getItemCount(): Int = mGames.size


    fun swap(newData: List<Game>) {
        mGames = newData
        notifyDataSetChanged()
    }
}