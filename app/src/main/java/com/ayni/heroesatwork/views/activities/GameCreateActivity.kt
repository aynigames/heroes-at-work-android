package com.ayni.heroesatwork.views.activities

import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.hideSoftKeyboard
import com.ayni.heroesatwork.application.launchActivity
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Player
import com.ayni.heroesatwork.views.adapters.HeroesAdapter
import com.ayni.heroesatwork.views.adapters.TagsAdapter
import com.ayni.heroesatwork.views.listeners.OnHeroDeletedListener
import com.ayni.heroesatwork.views.listeners.OnTagDeletedListener
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager


class GameCreateActivity : AppCompatActivity(), OnTagDeletedListener, OnHeroDeletedListener {

    @BindView(R.id.game_name_edit)
    lateinit var mGameNameEdit: EditText

    @BindView(R.id.game_points_seekbar)
    lateinit var mGamePointsSeekBar: SeekBar

    @BindView(R.id.game_points_text)
    lateinit var mGamePointsText: TextView

    @BindView(R.id.tags_recycler_view)
    lateinit var mTagsRecyclerView: RecyclerView

    @BindView(R.id.heroes_recycler_view)
    lateinit var mHeroesRecyclerView: RecyclerView

    var mTagList = mutableListOf<String>()
    var mHeroList = mutableListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_create_act)

        ButterKnife.bind(this)

        mTagsRecyclerView.setHasFixedSize(true)
        val layoutManager = FlexboxLayoutManager(this@GameCreateActivity)
        layoutManager.flexDirection = FlexDirection.ROW
        //layoutManager.justifyContent = JustifyContent.FLEX_START
        mTagsRecyclerView.layoutManager = layoutManager
        mTagsRecyclerView.adapter = TagsAdapter(mTagList, this@GameCreateActivity)

        mHeroesRecyclerView.setHasFixedSize(true)
        mHeroesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mHeroesRecyclerView.adapter = HeroesAdapter(mHeroList, this@GameCreateActivity)

        mGamePointsSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mGamePointsText.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    @OnClick(R.id.hero_add_button)
    internal fun onHeroAddClick() {
        launchActivity<HeroSearchActivity> {  }
    }

    @OnClick(R.id.game_create_button)
    internal fun onGameCreateClick() {
        val game = Game()
        game.name = mGameNameEdit.text.toString()

        if (game.name == "") {
            Toast.makeText(this@GameCreateActivity, "Please fill the game name", Toast.LENGTH_LONG).show()
            return
        }

        if (mTagList.count() == 0) {
            //TODO: ask if sure to create without tags
        }
        finish()
    }

    @OnClick(R.id.tag_add_button)
    internal fun onTagAddButton() {
        val alertDialog = AlertDialog.Builder(this@GameCreateActivity)
        val input = EditText(this@GameCreateActivity)
        input.setSingleLine()
        val container = FrameLayout(this@GameCreateActivity)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val marginSize = getResources().getDimensionPixelSize(R.dimen.heroes_margin)
        params.leftMargin = marginSize
        params.rightMargin = marginSize
        params.topMargin = marginSize
        params.bottomMargin = marginSize

        input.layoutParams = params
        container.addView(input)
        alertDialog.setTitle("Add Tag")
        alertDialog.setView(container)
        alertDialog.setPositiveButton("YES",
                object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val tag = input.getText().toString();
                        if (tag == "") {
                            Toast.makeText(this@GameCreateActivity, "Tag cannot be empty", Toast.LENGTH_LONG).show()
                            return
                        }
                        if (mTagList.any { t -> t.equals(tag, ignoreCase = true) }) {
                            Toast.makeText(this@GameCreateActivity, "Tag already added", Toast.LENGTH_LONG).show()
                            return
                        }
                        mTagList.add(tag)
                        mTagsRecyclerView.adapter.notifyDataSetChanged()
                        dialog!!.cancel()
                        this@GameCreateActivity.hideSoftKeyboard()
                    }
                })

        alertDialog.setNegativeButton("NO",
                object: DialogInterface.OnClickListener {
                    override fun onClick(dialog:DialogInterface?, which: Int) {
                        if (dialog != null) {
                            dialog.cancel()
                        }
                    }
                });

        alertDialog.show();
    }

    override fun onTagDeleted(tag: String) {
        mTagList.remove(tag)
        mTagsRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun onHeroDeleted(hero: Player) {
        mHeroList.remove(hero)
        mHeroesRecyclerView.adapter.notifyDataSetChanged()
    }

}