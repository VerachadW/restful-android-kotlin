package com.taskworld.android.restfulandroidkotlin.network.service

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService
import retrofit.RestAdapter
import retrofit.converter.Converter
import retrofit.converter.GsonConverter
import com.google.gson.GsonBuilder
import com.taskworld.android.restfulandroidkotlin.network.api.MovieDBApi
import com.taskworld.android.restfulandroidkotlin.util.Preference

class TheMovieAPISpiceService : RetrofitGsonSpiceService() {

    class object {

        val BASE_URL = "http://api.themoviedb.org/3"

        val API_KEY = "api_key"
        val API_VALUE = "11a11c4dcdc3909ab42b09a5e531b74f"

        val SESSION_KEY = "session_id"
    }

    override fun getServerUrl(): String? {
        return BASE_URL
    }

    override fun createRestAdapterBuilder(): RestAdapter.Builder? {
        var builder = super<RetrofitGsonSpiceService>.createRestAdapterBuilder()
        builder.setRequestInterceptor { requestInterceptor ->
            requestInterceptor.addQueryParam(API_KEY, API_VALUE)
            val sessionId = Preference.with(getApplicationContext()).sessionId
            if (sessionId != null) {
                requestInterceptor.addQueryParam(SESSION_KEY, sessionId)
            }
        }
            .setLogLevel(RestAdapter.LogLevel.FULL)
        return builder
    }

    override fun onCreate() {
        super<RetrofitGsonSpiceService>.onCreate()
        addRetrofitInterface(javaClass<MovieDBApi>())
    }

    override fun createConverter(): Converter? {
        return GsonConverter(GsonBuilder().setDateFormat("yyyy-MM-dd").create())
    }
}
