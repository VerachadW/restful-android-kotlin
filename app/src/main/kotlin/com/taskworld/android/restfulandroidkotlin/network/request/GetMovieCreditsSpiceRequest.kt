package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Cast
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 11/11/14.
 */

class GetMovieCreditsSpiceRequest(val path: String) :
        RetrofitSpiceRequest<Cast.CastList, TheMovieDBAPI.MovieAPI>(javaClass<Cast.CastList>(), javaClass<TheMovieDBAPI.MovieAPI>()) {

    override fun loadDataFromNetwork(): Cast.CastList? {
        return getService().getCastList(path)
    }
}
