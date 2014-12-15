package com.taskworld.android.restfulandroidkotlin.network2.action

import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network2.api.MovieDBApi
import io.realm.RealmResults

class MovieActionExecutor(val realm: Realm, val movieClass: Class<Movie>): MovieDBApi.MovieApi {

    override fun getMovies(category: String): Movie.ResultList {
        val result = realm.where(movieClass).findAll()
        val list: Movie.ResultList = Movie.ResultList()
        list.setResults(result)
        return list
    }

    override fun getMovie(id: Long): Movie {
        return realm.where(movieClass).equalTo("id", id).findFirst()
    }

}