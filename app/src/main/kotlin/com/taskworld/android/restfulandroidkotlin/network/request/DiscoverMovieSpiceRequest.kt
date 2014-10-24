package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.api.TheMovieDB

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */

class DiscoverMovieSpiceRequest(val sortBy: String) : RetrofitSpiceRequest<Movie.ResultList, TheMovieDB>(javaClass<Movie.ResultList>(), javaClass<TheMovieDB>()) {

    override fun loadDataFromNetwork(): Movie.ResultList? {
        return getService().discoverMovies(sortBy)
    }

}
