package com.ayni.heroesatwork.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.inflate
import com.ayni.heroesatwork.models.Player
import com.ayni.heroesatwork.views.listeners.OnHeroDeletedListener

class HeroesAdapter(private var mDataset: List<Player>, private var mOnHeroDeletedListener: OnHeroDeletedListener?): RecyclerView.Adapter<HeroesAdapter.HeroViewHolder>() {

    constructor(mDataset: List<Player>) : this(mDataset, null)

    class HeroViewHolder(itemView: View, private var onHeroDeletedListener: OnHeroDeletedListener?) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.hero_name_text)
        lateinit var mHeroNameTextView : TextView

        @BindView(R.id.hero_image)
        lateinit var mHeroImageView : ImageView

        private lateinit var mHero : Player

        fun bind(hero: Player) = with(itemView) {
            mHero = hero
            mHeroNameTextView.text = hero.playerFullName()
            //TODO: set image
            //mHeroImageView
        }

        @OnClick(R.id.hero_delete_button)
        fun onHeroDeleteButtonClick() {
            if (onHeroDeletedListener != null) {
                onHeroDeletedListener!!.onHeroDeleted(mHero)
            }
        }

        init {
            this.onHeroDeletedListener = onHeroDeletedListener
            ButterKnife.bind(this, itemView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            HeroViewHolder(parent.inflate(R.layout.hero_search_view), mOnHeroDeletedListener)

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(mDataset[position])
    }

    override fun getItemCount(): Int = mDataset.size


    fun swap(newData: List<Player>) {
        mDataset = newData
        notifyDataSetChanged()
    }

}