package com.taskworld.android.restfulandroidkotlin.network.api

import retrofit.http.GET
import retrofit.http.Query
import com.taskworld.android.restfulandroidkotlin.model.Movie

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */

trait TheMovieDBAPI {

    GET("/discover/movie")
    fun discoverMovies(Query("sort_by") sortBy: String): Movie.ResultList
}
