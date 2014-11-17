package com.taskworld.android.restfulandroidkotlin.activities.action

/**
 * Created by Kittinun Vantasin on 11/14/14.
 */

trait SignInUIAction {
    fun showProgress()
    fun hideProgress()
    fun setUnauthorizedError()
    fun navigateToMain(sessionId: String)
}