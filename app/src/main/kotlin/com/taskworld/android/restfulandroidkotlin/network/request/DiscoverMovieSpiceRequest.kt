package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */

class DiscoverMovieSpiceRequest(val sortBy: String) :
        RetrofitSpiceRequest<Movie.ResultList, TheMovieDBAPI>(javaClass<Movie.ResultList>(), javaClass<TheMovieDBAPI>()) {

    override fun loadDataFromNetwork(): Movie.ResultList? {
        return getService().discoverMovies(sortBy)
    }

}
