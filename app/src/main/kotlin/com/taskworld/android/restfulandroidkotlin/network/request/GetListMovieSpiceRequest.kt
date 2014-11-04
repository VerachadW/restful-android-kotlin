package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 10/29/14.
 */

class GetListMovieSpiceRequest(val path: String) :
        RetrofitSpiceRequest<Movie.ResultList, TheMovieDBAPI.MovieAPI>(javaClass<Movie.ResultList>(), javaClass<TheMovieDBAPI.MovieAPI>()) {

    override fun loadDataFromNetwork(): Movie.ResultList? {
        return getService().getList(path)
    }

}
