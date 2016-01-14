package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 11/11/14.
 */

class GetListMovieTopratedSpiceRequest(val path: String) :
        RetrofitSpiceRequest<Movie.ResultList, TheMovieDBAPI.MovieAPI>(Movie.ResultList::class.java, TheMovieDBAPI.MovieAPI::class.java) {

    override fun loadDataFromNetwork(): Movie.ResultList? {
        return service.getTopRatedList(path)
    }
}
