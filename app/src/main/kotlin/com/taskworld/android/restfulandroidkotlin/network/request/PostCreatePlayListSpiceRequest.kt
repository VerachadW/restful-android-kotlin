package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI
import com.taskworld.android.restfulandroidkotlin.model.PlayList

/**
 * Created by VerachadW on 11/18/14.
 */
class PostCreatePlayListSpiceRequest(val path: String, val entity: PlayList):
        RetrofitSpiceRequest<Map<String, String>, TheMovieDBAPI.ListAPI>(javaClass<Map<String, String>>(), javaClass<TheMovieDBAPI.ListAPI>()) {

    override fun loadDataFromNetwork(): Map<String, String>? {
        return getService().createPlayList(path, createRequestBody(entity))
    }

    fun createRequestBody(entity: PlayList): Map<String, String> {
        val map = hashMapOf<String, String>()
        map.put("name", entity.getName())
        map.put("description", entity.getDescription())
        return map
    }
}
