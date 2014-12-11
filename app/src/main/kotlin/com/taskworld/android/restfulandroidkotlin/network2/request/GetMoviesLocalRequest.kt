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
import io.realm.RealmResults
import com.taskworld.android.restfulandroidkotlin.extensions.createOrUpdate

/**
 * Created by VerachadW on 12/1/14.
 */
class GetMoviesLocalRequest(id:Long, val realm: Realm, val category: String)
    : RealmSpiceRequest<Movie.ResultList, MovieDBApi.MovieApi>(id, javaClass<Movie.ResultList>(), GetMoviesNetworkRequest(category)) {


    {
        event = OnMovieLoadedEvent(requestId)
        saveResultBlock = { result ->
            for(data in result.getResults()) {
                realm.createOrUpdate(javaClass<Movie>(), Pair("id", data.getId()), { clazz ->
                    val realmObject = realm.createObject(clazz)
                    realmObject.setTitle(data.getTitle())
                    realmObject.setOverview(data.getOverview() ?: "")
                    realmObject.setPopularity(data.getPopularity())
                    realmObject.setPosterPath(data.getPosterPath() ?: "")
                }, { clazz, oldData ->
                    oldData.setTitle(data.getTitle())
                    oldData.setOverview(data.getOverview() ?: "")
                    oldData.setPopularity(data.getPopularity())
                    oldData.setPosterPath(data.getPosterPath() ?: "")
                })
            }
        }
    }

    val executor: MovieActionExecutor = MovieActionExecutor(realm, javaClass<Movie>())

    override fun loadDataFromNetwork(): Movie.ResultList? {
        return executor.getMovies(category)
    }

}
