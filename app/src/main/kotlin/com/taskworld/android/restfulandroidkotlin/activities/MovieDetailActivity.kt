package com.taskworld.android.restfulandroidkotlin.activities

import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.R
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.content.Context
import android.content.Intent
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import com.taskworld.android.restfulandroidkotlin.model.Movie
import android.view.View
import android.widget.ImageView
import android.view.ViewGroup
import android.widget.TextView
import com.taskworld.android.restfulandroidkotlin.model.Cast
import com.taskworld.android.restfulandroidkotlin.adapter.ParallaxRecyclerAdapter
import android.os.Bundle
import com.squareup.picasso.Picasso
import android.support.v7.widget.Toolbar
import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient
import com.taskworld.android.restfulandroidkotlin.resource.router.ResourceRouterImpl
import com.taskworld.android.restfulandroidkotlin.utils.CircularTransform
import android.view.LayoutInflater

/**
 * Created by Kittinun Vantasin on 11/11/14.
 */

class MovieDetailActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId: Int = R.layout.activity_movie_detail

    //widgets
    //header
    var ivMovieCover: ImageView by Delegates.notNull()

    //toolbar
    val tbMovieDetail by Delegates.lazy { bindView<Toolbar>(R.id.tbMovieDetail) }
    val tvBarTitle by Delegates.lazy { bindView<TextView>(R.id.tvBarTitle) }

    //list
    val rvMovieCast by Delegates.lazy { bindView<RecyclerView>(R.id.rvMovieCast) }

    //adapter
    val mMovieCastAdapter by Delegates.lazy { ParallaxRecyclerAdapter(listOf<Cast>()) }

    //data
    var mCasts by Delegates.observable(listOf<Cast>(), { meta, oldCasts, newCasts ->
        mMovieCastAdapter.setData(newCasts)
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
        setSupportActionBar(tbMovieDetail)

        mMovieCastAdapter.implementRecyclerAdapterMethods(object : ParallaxRecyclerAdapter.RecyclerAdapterMethods {

            var mContext: Context by Delegates.notNull()

            override fun onCreateViewHolder(container: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
                mContext = container!!.getContext()
                val view = LayoutInflater.from(mContext).inflate(R.layout.recycle_view_item_movie_cast, container, false)
                return MovieCastViewHolder(view)
            }

            override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
                val cast = mCasts[position]

                val holder = viewHolder as MovieCastViewHolder

                Picasso.with(mContext).load("https://image.tmdb.org/t/p/w150/" + cast.getProfilePath())
                        .transform(CircularTransform())
                        .placeholder(R.drawable.ic_launcher)
                        .into(holder.ivCast)

                holder.tvCastName.setText(cast.getName())
                holder.tvCastCharacter.setText("as " + cast.getCharacter())
            }

            override fun getItemCount(): Int {
                return mCasts.size
            }
        })
        mMovieCastAdapter.setParallaxHeader(createHeaderView(), rvMovieCast)
        mMovieCastAdapter.setOnParallaxScroll { percentage, offset, parallaxView ->
            val d = tbMovieDetail.getBackground()
            d.setAlpha(Math.round(percentage * 255))
            tbMovieDetail.setBackground(d)
        }

        rvMovieCast.setLayoutManager(createLayoutManager())
        rvMovieCast.setAdapter(mMovieCastAdapter)

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
        val headerView = getLayoutInflater().inflate(R.layout.list_header_movie_detail, null)
        ivMovieCover = headerView.bindView<ImageView>(R.id.ivMovieDetailCover)
        return headerView
    }

    fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    inner class MovieCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCastName = itemView.bindView<TextView>(R.id.tvMovieCastName)
        val tvCastCharacter = itemView.bindView<TextView>(R.id.tvMovieCastCharacter)
        val ivCast = itemView.bindView<ImageView>(R.id.ivMovieCast)
    }
}
