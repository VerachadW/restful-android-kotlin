package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by VerachadW on 11/4/14.
 */
class ValidateTokenSpiceRequest (val username: String, val password: String, val token: String) :
        RetrofitSpiceRequest<Map<String, String>, TheMovieDBAPI.Authentication>(javaClass<Map<String, String>>(), javaClass<TheMovieDBAPI.Authentication>()) {


    override fun loadDataFromNetwork(): Map<String, String>? {
        return getService().validateToken(username, password, token)
    }

}