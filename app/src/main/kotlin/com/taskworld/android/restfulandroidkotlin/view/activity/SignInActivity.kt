package com.taskworld.android.restfulandroidkotlin.view.activity

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.action.SignInUIAction
import android.widget.Button
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import android.widget.EditText
import com.taskworld.android.restfulandroidkotlin.presenter.SignInPresenterImpl
import android.view.View
import android.widget.ProgressBar
import de.greenrobot.event.EventBus
import android.widget.TextView
import android.graphics.Color
import com.taskworld.android.restfulandroidkotlin.util.Preference

/**
 * Created by Kittinun Vantasin on 11/14/14.
 */

class SignInActivity : BaseSpiceActivity(), SignInUIAction {

    override val mContentLayoutResourceId: Int = R.layout.activity_signin

    //widgets
    val etUsername by Delegates.lazy { bindView<EditText>(R.id.etUsername) }
    val etPassword by Delegates.lazy { bindView<EditText>(R.id.etPassword) }

    val btSignIn by Delegates.lazy { bindView<Button>(R.id.btSignIn) }
    val pgProgress by Delegates.lazy { bindView<ProgressBar>(R.id.pgProgress) }
    val tvMessage by Delegates.lazy { bindView<TextView>(R.id.tvMessage) }

    //presenter
    val mPresenter by Delegates.lazy { SignInPresenterImpl(this, getServiceSpiceManager(), EventBus.getDefault()) }

    override fun setUp() {
        btSignIn.setOnClickListener { view ->
            mPresenter.logInWithCredentials(etUsername.getText().toString(), etPassword.getText().toString())
        }
    }

    override fun onResume() {
        super<BaseSpiceActivity>.onResume()
        mPresenter.onResume()
    }

    override fun onPause() {
        super<BaseSpiceActivity>.onPause()
        mPresenter.onPause()
    }

    override fun showProgress() {
        btSignIn.setVisibility(View.INVISIBLE)
        pgProgress.setVisibility(View.VISIBLE)
        tvMessage.setVisibility(View.GONE)
    }

    override fun hideProgress() {
        btSignIn.setVisibility(View.VISIBLE)
        pgProgress.setVisibility(View.INVISIBLE)
        tvMessage.setVisibility(View.GONE)
    }

    override fun setUnauthorizedError() {
        btSignIn.setVisibility(View.VISIBLE)
        pgProgress.setVisibility(View.INVISIBLE)

        tvMessage.setVisibility(View.VISIBLE)
        tvMessage.setTextColor(Color.RED)
        tvMessage.setText("Invalid username and/or password")
    }

    override fun setNetworkError() {
        btSignIn.setVisibility(View.VISIBLE)
        pgProgress.setVisibility(View.INVISIBLE)

        tvMessage.setVisibility(View.VISIBLE)
        tvMessage.setTextColor(Color.RED)
        tvMessage.setText("Network error, please try again")
    }

    override fun navigateToMain(sessionId: String) {
        Preference.with(this).sessionId = sessionId
        Preference.with(this).username = etUsername.getText().toString()

        startActivity(MainActivity.newIntent(this))
    }
}
