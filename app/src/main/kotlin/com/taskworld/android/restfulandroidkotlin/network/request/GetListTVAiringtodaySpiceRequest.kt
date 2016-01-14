package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.TV
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 11/11/14.
 */

class GetListTVAiringtodaySpiceRequest(val path: String) :
        RetrofitSpiceRequest<TV.ResultList, TheMovieDBAPI.TVAPI>(TV.ResultList::class.java, TheMovieDBAPI.TVAPI::class.java) {

    override fun loadDataFromNetwork(): TV.ResultList? {
        return service.getAiringTodayList(path)
    }

}