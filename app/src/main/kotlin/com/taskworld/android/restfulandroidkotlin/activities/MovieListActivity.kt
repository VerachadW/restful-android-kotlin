package com.taskworld.android.restfulandroidkotlin.activities

import com.taskworld.android.restfulandroidkotlin.R
import android.os.Bundle
import kotlin.properties.Delegates
import android.content.Context
import android.content.Intent
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.ListView
import android.widget.ArrayAdapter
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.extensions.tag
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.taskworld.android.restfulandroidkotlin.network.request.DiscoverMovieSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.response.EventBusResponseListener

/**
 * Created by Kittinun Vantasin on 10/28/14.
 */

class MovieListActivity : BaseServiceActivity() {

    override val mContentLayoutResourceId: Int = R.layout.activity_movie_list;

    val lvMovie by Delegates.lazy { bindView<ListView>(R.id.lvMovie) }

    val mMovieAdapter by Delegates.lazy { MovieAdapter() }

    var mItems by Delegates.observable(listOf<Movie>(), {
        meta, oldItems, newItems ->
        Log.i(tag(), "${oldItems.size} -> ${newItems.size}")
        mMovieAdapter.clear()
        mMovieAdapter.addAll(newItems)
        mMovieAdapter.notifyDataSetChanged()
    })

    class object {
        public fun newInstance(context: Context): Intent {
            return Intent(context, javaClass<MovieListActivity>())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseServiceActivity>.onCreate(savedInstanceState)

        lvMovie.setAdapter(mMovieAdapter)
        getServiceManager().execute(DiscoverMovieSpiceRequest("popularity.desc"), EventBusResponseListener<Movie.ResultList>())
    }

    fun onEvent(items: Movie.ResultList) {
        mItems = items.getResults()
    }

    inner class MovieAdapter : ArrayAdapter<Movie>(this,
            android.R.layout.simple_expandable_list_item_2, android.R.id.text1, mItems) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view = super<ArrayAdapter>.getView(position, convertView, parent)
            val tv1 = view.bindView<TextView>(android.R.id.text1)
            val tv2 = view.bindView<TextView>(android.R.id.text2)
            val movie = getItem(position)
            tv1.setText(movie.getTitle())
            tv2.setText(movie.getPopularity().toString())
            return view;
        }
    }
}
