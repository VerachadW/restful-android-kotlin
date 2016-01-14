package com.taskworld.android.restfulandroidkotlin.network.request

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest
import com.taskworld.android.restfulandroidkotlin.model.Image
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI

/**
 * Created by Kittinun Vantasin on 11/13/14.
 */

class GetMovieImagesSpiceRequest(val path: String) :
        RetrofitSpiceRequest<Image.PosterList, TheMovieDBAPI.MovieAPI>(Image.PosterList::class.java, TheMovieDBAPI.MovieAPI::class.java) {

    override fun loadDataFromNetwork(): Image.PosterList? {
        return service.getPosterImageList(path)
    }

}