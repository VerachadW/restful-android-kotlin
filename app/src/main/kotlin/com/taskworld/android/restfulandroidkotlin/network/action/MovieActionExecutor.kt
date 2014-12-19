package com.taskworld.android.restfulandroidkotlin.network.action

import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network.api.MovieDBApi
import io.realm.RealmResults
import java.util.ArrayList

class MovieActionExecutor(val realm: Realm, val movieClass: Class<Movie>): MovieDBApi.MovieApi {

    override fun getMovies(category: String): MovieDBApi.MovieApi.GetMoviesResponse {
        val result = realm.where(movieClass).findAll()
        val list = ArrayList(result)
        return MovieDBApi.MovieApi.GetMoviesResponse(list)
    }

    override fun getMovie(id: Long): Movie {
        return realm.where(movieClass).equalTo("id", id).findFirst()
    }

}