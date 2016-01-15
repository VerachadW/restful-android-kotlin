package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Cast
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 11/11/14.
 */

class GetMovieCreditsSpiceRequest(val path: String) :
        RetrofitSpiceRequest<Cast.CastList, TheMovieDBAPI.MovieAPI>(Cast.CastList::class.java, TheMovieDBAPI.MovieAPI::class.java) {

    override fun loadDataFromNetwork(): Cast.CastList? {
        return service.getCastList(path)
    }
}
