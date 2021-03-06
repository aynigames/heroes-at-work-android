package com.ayni.heroesatwork.views.activities

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.DateUtils
import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import com.ayni.heroesatwork.application.hideSoftKeyboard
import com.ayni.heroesatwork.application.launchActivity
import com.ayni.heroesatwork.models.Game
import com.ayni.heroesatwork.models.Player
import com.ayni.heroesatwork.viewmodels.GameViewModel
import com.ayni.heroesatwork.views.adapters.HeroesSearchAdapter
import com.ayni.heroesatwork.views.adapters.TagsAdapter
import com.ayni.heroesatwork.views.components.DatePickerFragment
import com.ayni.heroesatwork.views.listeners.OnHeroDeletedListener
import com.ayni.heroesatwork.views.listeners.OnTagDeletedListener
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class GameCreateActivity : AppCompatActivity(), OnTagDeletedListener, OnHeroDeletedListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.game_name_edit)
    lateinit var mGameNameEdit: EditText

    @BindView(R.id.game_start_date_text)
    lateinit var mGameStartDateText : TextView

    @BindView(R.id.game_end_date_text)
    lateinit var mGameEndDateText : TextView

    @BindView(R.id.game_points_seek_bar)
    lateinit var mGamePointsSeekBar: SeekBar

    @BindView(R.id.game_points_text)
    lateinit var mGamePointsText: TextView

    @BindView(R.id.tags_recycler_view)
    lateinit var mTagsRecyclerView: RecyclerView

    @BindView(R.id.heroes_recycler_view)
    lateinit var mHeroesRecyclerView: RecyclerView

    private var mGameStartDate: Date = DateUtils.getTodayStartOfDay()
    private var mGameEndDate: Date = DateUtils.getTodayEndOfDay()
    private var mTagList = mutableListOf<String>()
    private var mHeroList = mutableListOf<Player>()

    private lateinit var gameViewModel : GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_create_act)

        ButterKnife.bind(this)

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        updateDateDisplays()

        mTagsRecyclerView.setHasFixedSize(true)
        val layoutManager = FlexboxLayoutManager(this@GameCreateActivity)
        layoutManager.flexDirection = FlexDirection.ROW
        mTagsRecyclerView.layoutManager = layoutManager
        mTagsRecyclerView.adapter = TagsAdapter(mTagList, this@GameCreateActivity)

        mHeroesRecyclerView.setHasFixedSize(true)
        mHeroesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mHeroesRecyclerView.adapter = HeroesSearchAdapter(mHeroList, this@GameCreateActivity, null)

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
            setPositiveButton("Add",
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
            setNegativeButton("Cancel"
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

    @OnClick(R.id.hero_add_button)
    internal fun onHeroAddClick() {
        launchActivity<HeroSearchActivity>(HeroesAtWorkConstants.HERO_SEARCH_REQUEST_CODE) {  }
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

    override fun onHeroDeleted(hero: Player) {
        mHeroList.remove(hero)
        mHeroesRecyclerView.adapter.notifyDataSetChanged()
    }

    @OnClick(R.id.game_end_date_text)
    fun onEndDateClick() {
        val dialogFragment = DatePickerFragment()

        val arguments = Bundle()
        arguments.putLong(HeroesAtWorkConstants.MIN_DATE_KEY, DateUtils.getTodayStartOfDay().time)
        arguments.putLong(HeroesAtWorkConstants.SELECTED_DATE_KEY, DateUtils.getEndOfDay(mGameEndDate).time)
        dialogFragment.arguments = arguments

        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, 23, 59, 59)
        mGameEndDate = calendar.time
        updateDateDisplays()
    }

    private fun updateDateDisplays() {

        val sdf = DateFormat.getDateInstance()
        mGameStartDateText.text = sdf.format(mGameStartDate)
        mGameEndDateText.text = sdf.format(mGameEndDate)
    }

    @OnClick(R.id.game_create_button)
    internal fun onGameCreateClick() {
        val gameName = mGameNameEdit.text.toString()

        if (gameName == "") {
            Toast.makeText(this@GameCreateActivity, "Please fill the game name", Toast.LENGTH_LONG).show()
            return
        }

        if (mHeroList.count() == 0) {
            Toast.makeText(this@GameCreateActivity, "Please add some Heroes to the game", Toast.LENGTH_LONG).show()
            return
        }

        if (mTagList.count() == 0) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to create the game without tags?")
                    .setPositiveButton("YES", { dialog, _ ->
                        createGame()
                        dialog.cancel()
                    })
                    .setNegativeButton("NO", { dialog, _ ->
                        dialog.cancel()
                    })
            val alertDialog = builder.create()
            alertDialog.show()
        }
        else {
            createGame()
        }

    }

    private fun createGame() {
        val gameName = mGameNameEdit.text.toString()

        val game = Game()
        game.name = gameName
        val sdf = SimpleDateFormat(HeroesAtWorkConstants.DATE_FORMAT)
        game.putSetting(HeroesAtWorkConstants.SETTING_START_DATE, sdf.format(mGameStartDate))
        game.putSetting(HeroesAtWorkConstants.SETTING_END_DATE, sdf.format(mGameEndDate))
        game.putSetting(HeroesAtWorkConstants.SETTING_TAGS, mTagList.joinToString(separator = "|", transform = { t -> t }))
        game.putSetting(HeroesAtWorkConstants.SETTING_POINTS_PER_HERO, mGamePointsSeekBar.progress.toString())

        gameViewModel.createGame(game)

        finish()
    }
}