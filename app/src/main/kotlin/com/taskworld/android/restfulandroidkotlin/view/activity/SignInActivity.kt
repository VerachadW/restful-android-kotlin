package com.taskworld.android.restfulandroidkotlin.view.activity

import android.graphics.Color
import android.view.View
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.action.SignInUIAction
import com.taskworld.android.restfulandroidkotlin.presenter.SignInPresenterImpl
import com.taskworld.android.restfulandroidkotlin.util.Preference
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.activity_signin.*

/**
 * Created by Kittinun Vantasin on 11/14/14.
 */

class SignInActivity : BaseSpiceActivity(), SignInUIAction {

    override val mContentLayoutResourceId: Int = R.layout.activity_signin

    //presenter
    val mPresenter = SignInPresenterImpl(this, getServiceSpiceManager(), EventBus.getDefault())

    override fun setUp() {
        btSignIn.setOnClickListener {
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
        Preference.with(this).username = etUsername.text.toString()

        startActivity(MainActivity.newIntent(this))
    }
}
