package com.ayni.heroesatwork.views.activities

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
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
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.BaseCallback
import com.auth0.android.result.Credentials
import android.os.Looper
import android.os.Message
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import java.lang.Compiler.command
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.WebAuthProvider


class LoginActivity : HeroesAtWorkActivity() {

    //@BindView(R.id.email_edit)
    //lateinit var emailEditText : EditText

    //@BindView(R.id.password_edit)
    //lateinit var passwordEditText : EditText


    //private lateinit var memberViewModel : MemberViewModel

    lateinit var auth0: Auth0
    lateinit var authentication: AuthenticationAPIClient
    lateinit var storage: SharedPreferencesStorage
    lateinit var manager: CredentialsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth0 = Auth0(this)
        auth0.isOIDCConformant = true

        authentication = AuthenticationAPIClient(auth0);
        storage = SharedPreferencesStorage(this);
        manager = CredentialsManager(authentication, storage);

        var credentials = manager.getCredentials(object: BaseCallback<Credentials, CredentialsManagerException> {
            override fun onSuccess(payload: Credentials?) {
                goToMainMenu()
            }

            override fun onFailure(error: CredentialsManagerException?) {
                redirectToLogin()
            }
        })



        /*
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.login_act)

        ButterKnife.bind(this)

        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel::class.java)
        */
    }


    /*
    @OnClick(R.id.login_button)
    internal fun loginButtonClick() {
        //TODO: Validations for empty email and password
        //val email = emailEditText.text.toString()
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
    */

    fun redirectToLogin() {
        val mAuthenticationHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(message: Message) {
                if (message.what != 0) {
                    Toast.makeText(this@LoginActivity, message.obj.toString(), Toast.LENGTH_LONG).show()
                    finish()
                    return
                }

                var credentials = message.obj as Credentials
                manager.saveCredentials(credentials)
                goToMainMenu()
                Toast.makeText(this@LoginActivity, "Welcome", Toast.LENGTH_LONG).show()
            }
        }

        WebAuthProvider.init(auth0)
                .withScheme("https")
                .withScope("openid email")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                .start(this@LoginActivity, object : AuthCallback {
                    override fun onFailure(dialog: Dialog) {
                        val message = mAuthenticationHandler.obtainMessage(1, "There was an error authenticating")
                        message.sendToTarget()
                    }

                    override fun onFailure(exception: AuthenticationException) {
                        val message = mAuthenticationHandler.obtainMessage(2, "There was an error authenticating")
                        message.sendToTarget()
                    }

                    override fun onSuccess(credentials: Credentials) {
                        val message = mAuthenticationHandler.obtainMessage(0, credentials)
                        message.sendToTarget()
                    }
                })
    }

    internal fun goToMainMenu() {
        launchActivity<GameListActivity>()
    }

}
