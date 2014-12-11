package com.taskworld.android.restfulandroidkotlin.network.request

import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network2.api.MovieDBApi
import com.taskworld.android.restfulandroidkotlin.network2.request.NetworkSpiceRequest

/**
 * Created by Kittinun Vantasin on 10/29/14.
 */

class GetMoviesNetworkRequest(val category: String) :
        NetworkSpiceRequest<Movie.ResultList, MovieDBApi.MovieApi>(javaClass<Movie.ResultList>(), javaClass<MovieDBApi.MovieApi>()) {

    override fun loadDataFromNetwork(): Movie.ResultList {
        return getService().getMovies(category)
    }


}
