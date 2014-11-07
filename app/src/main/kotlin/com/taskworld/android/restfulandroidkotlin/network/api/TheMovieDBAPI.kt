package com.taskworld.android.restfulandroidkotlin.network.api

import retrofit.http.GET
import retrofit.http.Query
import com.taskworld.android.restfulandroidkotlin.model.Movie
import retrofit.http.POST
import retrofit.http.Body
import retrofit.http.EncodedPath
import com.taskworld.android.restfulandroidkotlin.model.TV

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */

trait TheMovieDBAPI {

    trait MovieAPI {
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
        fun validateToken(Query("username") username: String, Query("password") password: String, Query("request_token") token: String): Map<String, String>

        GET("/authentication/session/new")
        fun getNewSession(Query("request_token") token: String): Map<String, String>
    }

    trait TVAPI {
        GET("/{path}")
        fun get(EncodedPath("path") path: String): TV

        GET("/{path}")
        fun getList(EncodedPath("path") path: String): TV.ResultList

        POST("/{path}")
        fun post(EncodedPath("path") path: String, Body TV: TV)
    }
}

