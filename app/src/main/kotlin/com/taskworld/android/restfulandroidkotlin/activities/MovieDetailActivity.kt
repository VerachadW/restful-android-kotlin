package com.taskworld.android.restfulandroidkotlin.activities

import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.R
import android.content.Context
import android.content.Intent
import android.widget.ListView
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.ArrayAdapter
import com.taskworld.android.restfulandroidkotlin.network.resource.client.ResourceClient
import com.taskworld.android.restfulandroidkotlin.model.Movie
import android.view.View
import android.widget.ImageView
import android.view.ViewGroup
import android.widget.TextView
import com.taskworld.android.restfulandroidkotlin.model.Cast
import com.taskworld.android.restfulandroidkotlin.network.resource.router.ResourceRouterImpl
import android.os.Bundle
import com.squareup.picasso.Picasso
import android.graphics.Color
import android.support.v7.widget.Toolbar

/**
 * Created by Kittinun Vantasin on 11/11/14.
 */

class MovieDetailActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId: Int = R.layout.activity_movie_detail

    //widgets
    val tbMovieDetail by Delegates.lazy { bindView<Toolbar>(R.id.tbMovieDetail) }
    val tvBarTitle by Delegates.lazy { bindView<TextView>(R.id.tvBarTitle) }

    val lvMovieDetail by Delegates.lazy { bindView<ListView>(R.id.lvMovieDetail) }
    var ivMovieCover: ImageView by Delegates.notNull()

    //adapter
    val mMovieCastAdapter by Delegates.lazy { MovieCastAdapter() }

    //data
    var mCasts by Delegates.observable(listOf<Cast>(), { meta, oldCasts, newCasts ->
        mMovieCastAdapter.clear()
        mMovieCastAdapter.addAll(newCasts)
        mMovieCastAdapter.notifyDataSetChanged()
    })

    var mMovieId = 0

    class object {
        val ARG_MOVIE_ID = "movie_id"

        public fun newIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, javaClass<MovieDetailActivity>())
            intent.putExtra(ARG_MOVIE_ID, id)
            return intent
        }
    }

    override fun handleIntentExtras(intentExtras: Bundle) {
        mMovieId = intentExtras.getInt(ARG_MOVIE_ID)
    }

    override fun setUp() {
        lvMovieDetail.addHeaderView(createHeaderView())
        lvMovieDetail.setAdapter(mMovieCastAdapter)

        setSupportActionBar(tbMovieDetail)

        var client = ResourceClient.Builder()
                .setRouter(ResourceRouterImpl.newInstance(null, "credits"))
                .setSpiceManager(getServiceSpiceManager()).build()
        client.find(javaClass<Movie>(), mMovieId.toString())

        client = ResourceClient.Builder()
                .setRouter(ResourceRouterImpl.newInstance())
                .setSpiceManager(getServiceSpiceManager()).build()
        client.find(javaClass<Movie>(), mMovieId.toString())
    }

    fun onEvent(movie: Movie) {
        tvBarTitle.setText(movie.getTitle())
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + movie.getPosterPath()).into(ivMovieCover)
    }

    fun onEvent(casts: Cast.CastList) {
        mCasts = casts.getCasts()
    }

    fun createHeaderView(): View {
        ivMovieCover = getLayoutInflater().inflate(R.layout.list_header_movie_detail, null) as ImageView
        return ivMovieCover
    }

    inner class MovieCastAdapter : ArrayAdapter<Cast>(this,
            android.R.layout.simple_list_item_2, android.R.id.text1, mCasts) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view = super<ArrayAdapter>.getView(position, convertView, parent)
            val tv1 = view.bindView<TextView>(android.R.id.text1)
            val tv2 = view.bindView<TextView>(android.R.id.text2)
            val cast = getItem(position)
            tv1.setText(cast.getName())
            tv1.setTextColor(Color.DKGRAY)
            tv2.setText("as " + cast.getCharacter())
            tv2.setTextColor(Color.DKGRAY)
            return view;
        }
    }

}
