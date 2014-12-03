package com.taskworld.android.restfulandroidkotlin.network2.api

import retrofit.http.GET
import retrofit.http.Query
import com.taskworld.android.restfulandroidkotlin.model.Movie
import retrofit.http.Path

/**
 * Created by VerachadW on 11/26/14.
 */
trait MovieDBApi {

    trait Authentication {
        GET("/authentication/token/new")
        fun getRequestToken(): Map<String, String>

        GET("/authentication/token/validate_with_login")
        fun validateToken(Query("username") username: String, Query("password") password: String, Query("request_token") token: String): Map<String, String>

        GET("/authentication/session/new")
        fun getNewSession(Query("request_token") token: String): Map<String, String>
    }

    trait MovieApi {
        GET("/movie/{category}")
        fun getMovies(Path("category")category: String): Movie.ResultList

        GET("/movie/{id}")
        fun getMovie(Query("id") id: Long): Movie
    }
}