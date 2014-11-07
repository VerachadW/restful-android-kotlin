package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.TV
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 11/7/14.
 */

class GetListTVSpiceRequest(val path: String) :
        RetrofitSpiceRequest<TV.ResultList, TheMovieDBAPI.TVAPI>(javaClass<TV.ResultList>(), javaClass<TheMovieDBAPI.TVAPI>()) {

    override fun loadDataFromNetwork(): TV.ResultList? {
        return getService().getList(path)
    }

}
