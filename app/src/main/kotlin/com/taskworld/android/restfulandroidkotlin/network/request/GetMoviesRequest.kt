package com.taskworld.android.restfulandroidkotlin.network.request

import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network.api.MovieDBApi
import com.taskworld.android.restfulandroidkotlin.network.OnMovieLoadedEvent
import com.taskworld.android.restfulandroidkotlin.network.action.MovieActionExecutor
import com.taskworld.android.restfulandroidkotlin.extension.createOrUpdate


/**
 * Created by Johnny Dew on 12/12/2014 AD.
 */
class GetMoviesRequest(val realm: Realm, val category: String): BaseRestRequest<MovieDBApi.MovieApi.GetMoviesResponse, MovieDBApi.MovieApi>() {

    {
        saveResultBlock = { result ->
            for(data in result.movies) {
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

    override val event = OnMovieLoadedEvent(requestId)
    override val localRequest = GetMoviesLocalRequest(realm, category)
    override val networkRequest = GetMoviesNetworkRequest(category)
}


class GetMoviesLocalRequest(val realm: Realm, val category: String)
: BaseLocalRequest<MovieDBApi.MovieApi.GetMoviesResponse, MovieDBApi.MovieApi>(javaClass<MovieDBApi.MovieApi.GetMoviesResponse>()) {

    override fun loadDataFromNetwork(): MovieDBApi.MovieApi.GetMoviesResponse? {
        val executor = MovieActionExecutor(realm, javaClass<Movie>())
        return executor.getMovies(category)
    }

}

class GetMoviesNetworkRequest(val category: String) :
        BaseNetworkRequest<MovieDBApi.MovieApi.GetMoviesResponse, MovieDBApi.MovieApi>(javaClass<MovieDBApi.MovieApi.GetMoviesResponse>(), javaClass<MovieDBApi.MovieApi>()) {

    override fun loadDataFromNetwork(): MovieDBApi.MovieApi.GetMoviesResponse{
        return getService().getMovies(category)
    }


}
