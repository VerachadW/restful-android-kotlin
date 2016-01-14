package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by VerachadW on 11/4/14.
 */
class GetTokenSpiceRequest :
        RetrofitSpiceRequest<Map<*, *>, TheMovieDBAPI.Authentication>(Map::class.java, TheMovieDBAPI.Authentication::class.java) {

    override fun loadDataFromNetwork(): Map<String, String>? {
        return service.getRequestToken()
    }

}