package com.taskworld.android.restfulandroidkotlin.view.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import com.taskworld.android.restfulandroidkotlin.extension.toast
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient
import com.taskworld.android.restfulandroidkotlin.resource.router.ResourceRouterImpl
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 10/28/14.
 */

class MovieListActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId: Int = R.layout.activity_movie_list;

    //adapter
    val mMovieAdapter = MovieAdapter()

    //data
    var mItems by Delegates.observable(arrayListOf<Movie>(), { meta, oldItems, newItems ->
        mMovieAdapter.clear()
        mMovieAdapter.addAll(newItems)
        mMovieAdapter.notifyDataSetChanged()
    })

    val mRealm = Realm.getInstance(this)

    companion object {
        public fun newIntent(context: Context): Intent {
            return Intent(context, MovieListActivity::class.java)
        }
    }

    override fun setUp() {
        mItems.clear()
        lvMovie.adapter = mMovieAdapter
        val client = ResourceClient.Builder()
                .setRealm(mRealm)
                .setRouter(ResourceRouterImpl.newInstance("now_playing"))
                .setSpiceManager(getServiceSpiceManager()).build()
        client.findAll(Movie::class.java)
    }

    fun onEvent(items: Movie.ResultList) {
        mItems = items.results.toArrayList()

        val client = ResourceClient.Builder()
                .setRealm(mRealm)
                .setRouter(ResourceRouterImpl.newInstance())
                .setSpiceManager(getServiceSpiceManager()).build()
        client.find(Movie::class.java, "180299")
    }

    fun onEvent(item: Movie) {
        toast(item.title)
    }

    inner class MovieAdapter : ArrayAdapter<Movie>(this,
            android.R.layout.simple_expandable_list_item_2, android.R.id.text1, mItems) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view = super.getView(position, convertView, parent)
            val tv1 = view.bindView<TextView>(android.R.id.text1)
            val tv2 = view.bindView<TextView>(android.R.id.text2)
            val movie = getItem(position)
            tv1.text = movie.title
            tv2.text = movie.popularity.toString()
            return view;
        }
    }
}
