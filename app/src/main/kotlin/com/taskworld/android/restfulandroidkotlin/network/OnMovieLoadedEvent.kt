package com.taskworld.android.restfulandroidkotlin.network

import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.network.api.MovieDBApi

/**
 * Created by VerachadW on 12/2/14.
 */
class OnMovieLoadedEvent(id: Long): OnDataReceivedEvent<MovieDBApi.MovieApi.GetMoviesResponse>(id) {

}