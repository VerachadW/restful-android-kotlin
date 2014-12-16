package com.taskworld.android.restfulandroidkotlin.network

import com.taskworld.android.restfulandroidkotlin.model.Movie

/**
 * Created by VerachadW on 12/2/14.
 */
class OnMovieLoadedEvent(id: Long): OnDataReceivedEvent<Movie.ResultList>(id) {

}