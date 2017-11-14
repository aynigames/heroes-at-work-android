package com.ayni.heroesatwork.views.activities

import android.content.DialogInterface
import android.content.Intent
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
import com.ayni.heroesatwork.application.HeroesAtWorkConstants
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
import com.google.gson.Gson


class GameCreateActivity : AppCompatActivity(), OnTagDeletedListener, OnHeroDeletedListener {

    @BindView(R.id.game_name_edit)
    lateinit var mGameNameEdit: EditText

    @BindView(R.id.game_points_seek_bar)
    lateinit var mGamePointsSeekBar: SeekBar

    @BindView(R.id.game_points_text)
    lateinit var mGamePointsText: TextView

    @BindView(R.id.tags_recycler_view)
    lateinit var mTagsRecyclerView: RecyclerView

    @BindView(R.id.heroes_recycler_view)
    lateinit var mHeroesRecyclerView: RecyclerView

    private var mTagList = mutableListOf<String>()
    private var mHeroList = mutableListOf<Player>()

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
        mHeroesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mHeroesRecyclerView.adapter = HeroesAdapter(mHeroList, this@GameCreateActivity, null)

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
        launchActivity<HeroSearchActivity>(HeroesAtWorkConstants.HERO_SEARCH_REQUEST_CODE) {  }
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
        val marginSize = resources.getDimensionPixelSize(R.dimen.heroes_margin)
        params.leftMargin = marginSize
        params.rightMargin = marginSize
        params.topMargin = marginSize
        params.bottomMargin = marginSize

        input.layoutParams = params
        container.addView(input)
        with(alertDialog) {
            setTitle("Add Tag")
            setView(container)
            setPositiveButton("YES",
                DialogInterface.OnClickListener { dialog, _ ->
                    val tag = input.text.toString()
                    if (tag == "") {
                        Toast.makeText(this@GameCreateActivity, "Tag cannot be empty", Toast.LENGTH_LONG).show()
                        return@OnClickListener
                    }
                    if (mTagList.any { t -> t.equals(tag, ignoreCase = true) }) {
                        Toast.makeText(this@GameCreateActivity, "Tag already added", Toast.LENGTH_LONG).show()
                        return@OnClickListener
                    }
                    mTagList.add(tag)
                    mTagsRecyclerView.adapter.notifyDataSetChanged()
                    dialog!!.cancel()
                    this@GameCreateActivity.hideSoftKeyboard()
                })
            setNegativeButton("NO"
            ) { dialog, _ ->
                dialog?.cancel()
            }
        }

        alertDialog.show()
    }

    override fun onTagDeleted(tag: String) {
        mTagList.remove(tag)
        mTagsRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun onHeroDeleted(hero: Player) {
        mHeroList.remove(hero)
        mHeroesRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HeroesAtWorkConstants.HERO_SEARCH_REQUEST_CODE) {
            val bundle = data!!.getBundleExtra(HeroesAtWorkConstants.HERO_SELECTED_BUNDLE_KEY)
            val heroJson = bundle.getString(HeroesAtWorkConstants.HERO_SELECTED_BUNDLE_KEY)
            val hero = Gson().fromJson<Player>(heroJson, Player::class.java)
            if (mHeroList.any { h -> h.memberId == hero.memberId }) {
                Toast.makeText(this@GameCreateActivity, "Hero already added", Toast.LENGTH_LONG).show()
                return
            }
            mHeroList.add(hero)
            mHeroesRecyclerView.adapter.notifyDataSetChanged()
        }
    }

}