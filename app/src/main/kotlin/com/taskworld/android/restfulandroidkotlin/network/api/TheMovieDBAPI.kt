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

    trait MovieAPI {
        //movie
        GET("/{path}")
        fun get(EncodedPath("path") path: String): Movie

        GET("/{path}")
        fun getList(EncodedPath("path") path: String): Movie.ResultList

        POST("/{path}")
        fun post(EncodedPath("path") path: String, Body movie: Movie)
    }

    trait Authentication {
        GET("/authentication/token/new")
        fun getRequestToken(): Map<String, String>

        GET("/authentication/token/validate_with_login")
        fun validateToken(): Map<String, String>

        GET("/authentication/session/new")
        fun getNewSession(): Map<String, String>
    }

}

