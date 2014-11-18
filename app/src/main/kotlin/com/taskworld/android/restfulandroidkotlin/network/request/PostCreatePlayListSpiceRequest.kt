package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI
import com.taskworld.android.restfulandroidkotlin.model.PlayList

/**
 * Created by VerachadW on 11/18/14.
 */
class PostCreatePlayListSpiceRequest(val path: String, val body: Map<String, String>):
        RetrofitSpiceRequest<Map<String, String>, TheMovieDBAPI.ListAPI>(javaClass<Map<String, String>>(), javaClass<TheMovieDBAPI.ListAPI>()) {

    override fun loadDataFromNetwork(): Map<String, String>? {
        return getService().createPlayList(path, body)
    }
}
