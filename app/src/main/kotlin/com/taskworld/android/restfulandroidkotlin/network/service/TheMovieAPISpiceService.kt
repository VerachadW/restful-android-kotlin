package com.taskworld.android.restfulandroidkotlin.network.service

import com.google.gson.GsonBuilder
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService
import com.taskworld.android.restfulandroidkotlin.network.api.TheMovieDBAPI
import retrofit.RestAdapter
import retrofit.converter.Converter
import retrofit.converter.GsonConverter

class TheMovieAPISpiceService : RetrofitGsonSpiceService() {

    companion object {

        val BASE_URL = "http://api.themoviedb.org/3"

        val API_KEY = "api_key"
        val API_VALUE = "11a11c4dcdc3909ab42b09a5e531b74f"
    }

    override fun getServerUrl(): String? {
        return BASE_URL
    }

    override fun createRestAdapterBuilder(): RestAdapter.Builder? {
        var builder = super<RetrofitGsonSpiceService>.createRestAdapterBuilder()
        builder.setRequestInterceptor { requestInterceptor -> requestInterceptor.addQueryParam(API_KEY, API_VALUE) }
                .setLogLevel(RestAdapter.LogLevel.FULL)
        return builder
    }

    override fun onCreate() {
        super<RetrofitGsonSpiceService>.onCreate()
        addRetrofitInterface(TheMovieDBAPI::class.java)
    }

    override fun createConverter(): Converter? {
        return GsonConverter(GsonBuilder().setDateFormat("yyyy-MM-dd").create())
    }
}
