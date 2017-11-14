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
import com.ayni.heroesatwork.views.listeners.OnHeroSelectedListener

class HeroesAdapter(
        private var mHeroes: List<Player>,
        private var mOnHeroDeletedListener: OnHeroDeletedListener?,
        private var mOnHeroSelectedListener: OnHeroSelectedListener?): RecyclerView.Adapter<HeroesAdapter.HeroViewHolder>() {

    class HeroViewHolder(
            itemView: View,
            private var onHeroDeletedListener: OnHeroDeletedListener?,
            private var onHeroSelectedListener: OnHeroSelectedListener?) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.hero_name_text)
        lateinit var mHeroNameTextView : TextView

        @BindView(R.id.hero_image)
        lateinit var mHeroImageView : ImageView

        @BindView(R.id.hero_delete_button)
        lateinit var mHeroDeleteButton : ImageView

        private lateinit var mHero : Player

        fun bind(hero: Player) = with(itemView) {
            mHero = hero
            mHeroNameTextView.text = hero.playerFullName()
            //TODO: set image
            //mHeroImageView
            if (onHeroDeletedListener == null) {
                mHeroDeleteButton.visibility = View.GONE
            }
            if (onHeroSelectedListener != null) {
                itemView.setOnClickListener { onHeroSelectedListener?.onHeroSelected(hero) }
            }
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
            HeroViewHolder(parent.inflate(R.layout.hero_search_view), mOnHeroDeletedListener, mOnHeroSelectedListener)

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(mHeroes[position])
    }

    override fun getItemCount(): Int = mHeroes.size


    fun swap(newData: List<Player>) {
        mHeroes = newData
        notifyDataSetChanged()
    }

}