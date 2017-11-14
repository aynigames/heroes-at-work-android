package com.ayni.heroesatwork.views.activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ayni.heroesatwork.R
import com.ayni.heroesatwork.application.HeroesAtWorkActivity
import com.ayni.heroesatwork.application.HeroesAtWorkConstants
import com.ayni.heroesatwork.application.PreferenceHelper
import com.ayni.heroesatwork.application.launchActivity
import com.ayni.heroesatwork.viewmodels.MemberViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginActivity : HeroesAtWorkActivity() {

    @BindView(R.id.email_edit)
    lateinit var emailEditText : EditText

    @BindView(R.id.password_edit)
    lateinit var passwordEditText : EditText


    private lateinit var memberViewModel : MemberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.login_act)

        ButterKnife.bind(this)

        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel::class.java)
    }

    @OnClick(R.id.login_button)
    internal fun loginButtonClick() {
        //TODO: Validations for empty email and password
        val email = emailEditText.text.toString()
        //val password = passwordEditText.text.toString()

        memberViewModel.authenticate(email).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { member -> run {
                            PreferenceHelper.saveStringPreference(HeroesAtWorkConstants.MEMBER_EMAIL_PREFERENCE_KEY, member.email)
                            PreferenceHelper.saveIntPreference(HeroesAtWorkConstants.MEMBER_ID_PREFERENCE_KEY, member.memberId)
                            launchActivity<GameListActivity> {  }
                        } },
                        { _ -> Toast.makeText(this@LoginActivity, "There was authenticating. Please retry later.", Toast.LENGTH_LONG).show() }
                )
    }

    @OnClick(R.id.login_facebook_button)
    internal fun loginFacebookButtonClick() {
        launchActivity<GameListActivity>()
    }

}
