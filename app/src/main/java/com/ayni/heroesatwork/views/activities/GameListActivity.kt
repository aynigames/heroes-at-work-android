package com.ayni.heroesatwork.views.activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.launchActivity
import com.ayni.heroesatwork.viewmodels.GameViewModel
import com.ayni.heroesatwork.views.adapters.CurrentGamesAdapter
import com.ayni.heroesatwork.views.adapters.OldGamesAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GameListActivity : AppCompatActivity() {

    @BindView(R.id.fab)
    lateinit var fab: FloatingActionButton

    @BindView(R.id.current_games_recycler_view)
    lateinit var mCurrentGamesRecyclerView: RecyclerView

    @BindView(R.id.old_games_recycler_view)
    lateinit var mOldGamesRecyclerView: RecyclerView

    private lateinit var gameViewModel : GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_list_act)

        ButterKnife.bind(this)

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        mCurrentGamesRecyclerView.setHasFixedSize(true)
        mCurrentGamesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mCurrentGamesRecyclerView.adapter = CurrentGamesAdapter(emptyList())

        mOldGamesRecyclerView.setHasFixedSize(true)
        mOldGamesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mOldGamesRecyclerView.adapter = OldGamesAdapter(emptyList())

        fab.setOnClickListener { view ->
            launchActivity<GameCreateActivity> {  }
        }
    }

    override fun onStart() {
        super.onStart()
        loadGames()
    }

    private fun loadGames() {
        gameViewModel.getCurrentGames().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { games -> (mCurrentGamesRecyclerView.adapter as CurrentGamesAdapter).swap(games) },
                        { _ -> Toast.makeText(this@GameListActivity, "There was an error obtaining your games.", Toast.LENGTH_LONG).show() }
                )

        gameViewModel.getOldGames().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { games -> (mOldGamesRecyclerView.adapter as OldGamesAdapter).swap(games) },
                        { _ -> Toast.makeText(this@GameListActivity, "There was an error obtaining your games.", Toast.LENGTH_LONG).show() }
                )
    }
}