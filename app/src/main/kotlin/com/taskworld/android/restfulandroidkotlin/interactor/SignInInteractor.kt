package com.taskworld.android.restfulandroidkotlin.interactor

import com.octo.android.robospice.SpiceManager
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.network.RestfulResourceClient
import com.taskworld.android.restfulandroidkotlin.network.request.GetTokenRequest
import com.taskworld.android.restfulandroidkotlin.network.request.GetNewSessionRequest
import com.taskworld.android.restfulandroidkotlin.network.request.ValidateTokenRequest

/**
 * Created by Kittinun Vantasin on 11/14/14.
 */

trait SignInInteractor {
    fun requestNewToken()
    fun requestNewSession(requestToken: String)
    fun validateCredentials(username: String, password: String, requestToken: String)
}

class SignInInteractorImpl(val mClient: RestfulResourceClient) : SignInInteractor {
    override fun requestNewToken() {
        mClient.execute(GetTokenRequest())
    }

    override fun requestNewSession(requestToken: String) {
        mClient.execute(GetNewSessionRequest(requestToken))
    }

    override fun validateCredentials(username: String, password: String, requestToken: String) {
        mClient.execute(ValidateTokenRequest(username, password, requestToken))
    }

}

