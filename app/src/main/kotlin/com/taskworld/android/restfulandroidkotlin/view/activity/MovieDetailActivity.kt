package com.taskworld.android.restfulandroidkotlin.view.activity

import kotlin.properties.Delegates
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.content.Context
import android.content.Intent
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import com.taskworld.android.restfulandroidkotlin.model.Movie
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.taskworld.android.restfulandroidkotlin.model.Cast
import com.taskworld.android.restfulandroidkotlin.adapter.ParallaxRecyclerAdapter
import android.os.Bundle
import com.squareup.picasso.Picasso
import android.support.v7.widget.Toolbar
import com.taskworld.android.restfulandroidkotlin.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.view.ViewPager
import android.support.v4.view.PagerAdapter
import com.taskworld.android.restfulandroidkotlin.model.Image
import android.view.ViewGroup.LayoutParams
import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient
import com.taskworld.android.restfulandroidkotlin.resource.router.ResourceRouterImpl
import com.taskworld.android.restfulandroidkotlin.util.CircularTransformation

/**
 * Created by Kittinun Vantasin on 11/11/14.
 */

class MovieDetailActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId: Int = R.layout.activity_movie_detail

    //widgets
    //header
    var tvMovieOverview: TextView by Delegates.notNull()
    var vpMovieCover: ViewPager by Delegates.notNull()

    //toolbar
    val tbMovieDetail by Delegates.lazy { bindView<Toolbar>(R.id.tbMovieDetail) }
    val tvBarTitle by Delegates.lazy { bindView<TextView>(R.id.tvBarTitle) }

    //list
    val rvMovieCast by Delegates.lazy { bindView<RecyclerView>(R.id.rvMovieCast) }

    //adapter
    val mMovieCastAdapter by Delegates.lazy { ParallaxRecyclerAdapter(listOf<Cast>()) }
    val mMovieCoverAdapter by Delegates.lazy { MovieCoverImageAdapter() }

    //data
    var mMovieId = 0
    var mCasts by Delegates.observable(listOf<Cast>(), { meta, oldCasts, newCasts ->
        mMovieCastAdapter.setData(newCasts)
    })
    var mCoverImages by Delegates.observable(listOf<Image>(), { meta, oldCoverImages, newCoverImages ->
        mMovieCoverAdapter.data = newCoverImages
    })

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

        mMovieCastAdapter.implementRecyclerAdapterMethods(MoveCastParallaxRecyclerAdapter())
        mMovieCastAdapter.setParallaxHeader(createHeaderView(), rvMovieCast)
        mMovieCastAdapter.setOnParallaxScroll { percentage, offset, parallaxView ->
            val d = tbMovieDetail.getBackground()
            d.setAlpha(Math.round((percentage) * 255))
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

        client = ResourceClient.Builder()
                .setRouter(ResourceRouterImpl.newInstance(null, "images"))
                .setSpiceManager(getServiceSpiceManager()).build()
        client.find(javaClass<Movie>(), mMovieId.toString())
    }

    fun onEvent(movie: Movie) {
        tvBarTitle.setText(movie.getTitle())
        tvMovieOverview.setText(movie.getOverview())
    }

    fun onEvent(casts: Cast.CastList) {
        mCasts = casts.getCasts()
    }

    fun onEvent(images: Image.PosterList) {
        mCoverImages = images.getResults()
    }

    fun createHeaderView(): View {
        val headerView = getLayoutInflater().inflate(R.layout.list_header_movie_detail, null)
        tvMovieOverview = headerView.bindView<TextView>(R.id.tvMovieOverview)
        vpMovieCover = headerView.bindView<ViewPager>(R.id.vpMovieCover)
        vpMovieCover.setAdapter(mMovieCoverAdapter)
        return headerView
    }

    fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    inner class MovieCoverImageAdapter : PagerAdapter() {

        var data = listOf<Image>()
            set (value) {
                $data = value
                notifyDataSetChanged()
            }

        override fun getCount(): Int {
            return data.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any? {
            val viewPagerItem = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager_item_movie_cover, container, false)
            val ivMovieCover = viewPagerItem.bindView<ImageView>(R.id.ivMovieCover)
            val tvMovieCoverVoteCount = viewPagerItem.bindView<TextView>(R.id.tvMovieCoverVoteCount)

            //bind views
            val image = mCoverImages[position]
            Picasso.with(container.getContext()).load("https://image.tmdb.org/t/p/w500/" + image.getFilePath()).into(ivMovieCover)
            tvMovieCoverVoteCount.setText(image.getVoteCount().toString())

            container.addView(viewPagerItem, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            return viewPagerItem
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }
    }

    inner class MovieCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCastName = itemView.bindView<TextView>(R.id.tvMovieCastName)
        val tvCastCharacter = itemView.bindView<TextView>(R.id.tvMovieCastCharacter)
        val ivCast = itemView.bindView<ImageView>(R.id.ivMovieCast)
    }

    inner class MoveCastParallaxRecyclerAdapter : ParallaxRecyclerAdapter.RecyclerAdapterMethods {

        var mContext: Context by Delegates.notNull()

        override fun onCreateViewHolder(container: ViewGroup?, i: Int): RecyclerView.ViewHolder? {
            mContext = container!!.getContext()
            val view = LayoutInflater.from(mContext).inflate(R.layout.recycle_view_item_movie_cast, container, false)
            return MovieCastViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?, position: Int) {

            val cast = mCasts[position]
            val holder = viewHolder as MovieCastViewHolder
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w150/" + cast.getProfilePath())
                    .transform(CircularTransformation())
                    //.placeholder(R.drawable.ic_launcher)
                    .into(holder.ivCast)
            holder.tvCastName.setText(cast.getName())
            holder.tvCastCharacter.setText("as " + cast.getCharacter())
        }

        override fun getItemCount(): Int {
            return mCasts.size
        }

    }
}
