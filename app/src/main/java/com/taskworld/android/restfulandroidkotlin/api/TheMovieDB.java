package com.taskworld.android.restfulandroidkotlin.api;

import com.taskworld.android.restfulandroidkotlin.model.Movie;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Kittinun Vantasin on 10/24/14.
 */
public interface TheMovieDB {

    @GET("/discover/movie")
    Movie.ResultList discoverMovies(@Query("sort_by") String sortBy);
}
