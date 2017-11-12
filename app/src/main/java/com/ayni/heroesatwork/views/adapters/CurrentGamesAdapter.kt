package com.ayni.heroesatwork.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.inflate
import com.ayni.heroesatwork.application.launchActivity
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.views.activities.GameDetailActivity
import java.util.*

class CurrentGamesAdapter (var mDataset: List<Game>?): RecyclerView.Adapter<CurrentGamesAdapter.CurrentGameViewHolder>() {

    class CurrentGameViewHolder : RecyclerView.ViewHolder {

        @BindView((R.id.game_name_text))
        lateinit var nameTextView: TextView

        @BindView(R.id.game_remaining_days_text)
        lateinit var remainingDaysTextView: TextView

        constructor(itemView: View): super(itemView) {
            ButterKnife.bind(this, itemView)
        }

        fun bind(game: Game, listener: (Context, Game) -> Unit) = with(itemView) {
            nameTextView.text = game.name
            var today = Date()
            var diff = (game.endedOn!!.time - today.time) / (24 * 60 * 60 * 1000)
            if (diff < 0)
                remainingDaysTextView.text = "Just Finished"
            else if (diff == 0L)
                remainingDaysTextView.text = "Finishes today"
            else if (diff == 1L)
                remainingDaysTextView.text = "Finishes tomorrow"
            else
                remainingDaysTextView.text = "Finishes in $diff days"

            //TODO: Heroes faces


            itemView.setOnClickListener { listener(context, game) }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CurrentGameViewHolder(parent.inflate(R.layout.current_game_view))

    override fun onBindViewHolder(holder: CurrentGameViewHolder, position: Int) {
        holder.bind(mDataset!![position], listener)
    }

    override fun getItemCount(): Int = if (mDataset != null) mDataset!!.size else 0


    fun swap(newData: List<Game>?) {
        mDataset = newData
        notifyDataSetChanged()
    }

    object listener : (Context, Game) -> Unit {
        override fun invoke(context: Context, game: Game) {
            context.launchActivity<GameDetailActivity> {  }
        }
    }
}