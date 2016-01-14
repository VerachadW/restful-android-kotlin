package com.taskworld.android.restfulandroidkotlin.interactor

import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.request.GetNewSessionSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.request.GetTokenSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.request.ValidateTokenSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.response.EventBusRequestListener
import de.greenrobot.event.EventBus

/**
 * Created by Kittinun Vantasin on 11/14/14.
 */

interface SignInInteractor {
    fun requestNewToken()
    fun requestNewSession(requestToken: String)
    fun validateCredentials(username: String, password: String, requestToken: String)
}

class SignInInteractorImpl(val mSpiceManager: SpiceManager, val mBus: EventBus) : SignInInteractor {
    override fun requestNewToken() {
        mSpiceManager.execute(GetTokenSpiceRequest(), EventBusRequestListener.newInstance(mBus))
    }

    override fun requestNewSession(requestToken: String) {
        mSpiceManager.execute(GetNewSessionSpiceRequest(requestToken), EventBusRequestListener.newInstance(mBus))
    }

    override fun validateCredentials(username: String, password: String, requestToken: String) {
        mSpiceManager.execute(ValidateTokenSpiceRequest(username, password, requestToken), EventBusRequestListener.newInstance(mBus))
    }

}

