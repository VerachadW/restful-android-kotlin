package com.taskworld.android.restfulandroidkotlin.network2.action

import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network2.api.MovieDBApi

class MovieActionExecutor(realm: Realm, movieClass: Class<Movie>): ActionExecutor<Movie>(realm, movieClass), MovieDBApi.MovieApi {

    override fun getMovies(category: String): Movie.ResultList {
        val result = findAll()

        val list: Movie.ResultList = Movie.ResultList()
        list.setResults(result)
        return list
    }

    override fun getMovie(id: Long): Movie {
        val result = find()
        return result
    }

}