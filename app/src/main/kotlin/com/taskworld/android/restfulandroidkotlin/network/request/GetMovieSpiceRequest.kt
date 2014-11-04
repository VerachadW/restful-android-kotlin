package com.taskworld.android.restfulandroidkotlin.network.request

import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest

/**
 * Created by Kittinun Vantasin on 10/29/14.
 */

class GetMovieSpiceRequest(val path: String) :
        RetrofitSpiceRequest<Movie, TheMovieDBAPI.MovieAPI>(javaClass<Movie>(), javaClass<TheMovieDBAPI.MovieAPI>()) {

    override fun loadDataFromNetwork(): Movie? {
        return getService().get(path)
    }

}
