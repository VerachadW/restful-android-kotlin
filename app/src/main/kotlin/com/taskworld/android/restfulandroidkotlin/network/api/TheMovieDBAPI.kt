package com.taskworld.android.restfulandroidkotlin.network.api

import retrofit.http.GET
import retrofit.http.Query
import com.taskworld.android.restfulandroidkotlin.model.Movie
import retrofit.http.POST
import retrofit.http.Body
import retrofit.http.Path
import retrofit.http.EncodedPath

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */

trait TheMovieDBAPI {

    deprecated("Use getList(...) instead")
    GET("/discover/movie")
    fun discoverMovies(Query("sort_by") sortBy: String): Movie.ResultList

    trait MovieAPI {
        //movie
        GET("/{path}")
        fun get(EncodedPath("path") path: String): Movie

        GET("/{path}")
        fun getList(EncodedPath("path") path: String): Movie.ResultList

        POST("/{path}")
        fun post(EncodedPath("path") path: String, Body movie: Movie)
    }
}

