package com.ayni.heroesatwork.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.inflate
import com.ayni.heroesatwork.views.listeners.OnTagDeletedListener

class TagsAdapter(private var mDataset: List<String>?, private var mOnTagDeletedListener: OnTagDeletedListener): RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    class TagViewHolder(itemView: View, private var onTagDeletedListener: OnTagDeletedListener) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tag_text)
        lateinit var mTagTextView: TextView

        fun bind(tag: String) = with(itemView) {
            mTagTextView.text = tag
        }

        @OnClick(R.id.tag_delete_button)
        fun onTagDeleteButtonClick() {
            onTagDeletedListener.onTagDeleted(mTagTextView.text.toString())
        }

        init {
            ButterKnife.bind(this, itemView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TagViewHolder(parent.inflate(R.layout.tag_view), mOnTagDeletedListener)

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(mDataset!![position])
    }

    override fun getItemCount(): Int = if (mDataset != null) mDataset!!.size else 0


    fun swap(newData: List<String>?) {
        mDataset = newData
        notifyDataSetChanged()
    }

}