package com.taskworld.android.restfulandroidkotlin.network2.request

import com.octo.android.robospice.request.SpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Movie
import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.network2.action.MovieActionExecutor
import com.taskworld.android.restfulandroidkotlin.network2.OnDataReceivedEvent
import com.taskworld.android.restfulandroidkotlin.network2.OnMovieLoadedEvent
import com.taskworld.android.restfulandroidkotlin.network2.api.MovieDBApi
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.request.GetMoviesNetworkRequest

/**
 * Created by VerachadW on 12/1/14.
 */
class GetMoviesLocalRequest(id:Long, realm: Realm, val category: String)
    : RealmSpiceRequest<Movie.ResultList, MovieDBApi.MovieApi>(id, javaClass<Movie.ResultList>(), GetMoviesNetworkRequest(category)) {

    {
        event = OnMovieLoadedEvent(requestId)
    }

    val executor: MovieActionExecutor = MovieActionExecutor(realm, javaClass<Movie>())

    override fun loadDataFromNetwork(): Movie.ResultList? {
        return executor.getMovies(category)
    }

}
