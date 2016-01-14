package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 10/29/14.
 */

class GetMovieSpiceRequest(val path: String) :
        RetrofitSpiceRequest<Movie, TheMovieDBAPI.MovieAPI>(Movie::class.java, TheMovieDBAPI.MovieAPI::class.java) {

    override fun loadDataFromNetwork(): Movie? {
        return service.get(path)
    }

}
