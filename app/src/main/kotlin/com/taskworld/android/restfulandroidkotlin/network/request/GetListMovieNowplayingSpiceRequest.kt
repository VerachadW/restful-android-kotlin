package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 11/11/14.
 */

class GetListMovieNowplayingSpiceRequest(val path: String) :
        RetrofitSpiceRequest<Movie.ResultList, TheMovieDBAPI.MovieAPI>(javaClass<Movie.ResultList>(), javaClass<TheMovieDBAPI.MovieAPI>()) {

    override fun loadDataFromNetwork(): Movie.ResultList? {
        return getService().getNowPlayingList(path)
    }
}
