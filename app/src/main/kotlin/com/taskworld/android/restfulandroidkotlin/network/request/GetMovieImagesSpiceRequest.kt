package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Image
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 11/13/14.
 */

class GetMovieImagesSpiceRequest(val path: String) :
        RetrofitSpiceRequest<Image.PosterList, TheMovieDBAPI.MovieAPI>(javaClass<Image.PosterList>(), javaClass<TheMovieDBAPI.MovieAPI>()) {

    override fun loadDataFromNetwork(): Image.PosterList? {
        return getService().getPosterImageList(path)
    }

}