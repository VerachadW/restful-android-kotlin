package com.taskworld.android.restfulandroidkotlin.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.adapter.ParallaxRecyclerAdapter
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import com.taskworld.android.restfulandroidkotlin.model.Cast
import com.taskworld.android.restfulandroidkotlin.model.Image
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient
import com.taskworld.android.restfulandroidkotlin.resource.router.ResourceRouterImpl
import com.taskworld.android.restfulandroidkotlin.util.CircularTransformation
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 11/11/14.
 */

class MovieDetailActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId: Int = R.layout.activity_movie_detail

    //header
    var tvMovieOverview: TextView by Delegates.notNull()
    var vpMovieCover: ViewPager by Delegates.notNull()

    //adapter
    val mMovieCastAdapter = ParallaxRecyclerAdapter(listOf<Cast>())
    val mMovieCoverAdapter = MovieCoverImageAdapter()

    //data
    var mMovieId = 0
    var mCasts by Delegates.observable(listOf<Cast>(), { meta, oldCasts, newCasts ->
        mMovieCastAdapter.setData(newCasts)
    })
    var mCoverImages by Delegates.observable(listOf<Image>(), { meta, oldCoverImages, newCoverImages ->
        mMovieCoverAdapter.data = newCoverImages
    })

    companion object {
        val ARG_MOVIE_ID = "movie_id"

        public fun newIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
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
            val d = tbMovieDetail.background
            d.alpha = Math.round((percentage) * 255)
            tbMovieDetail.background = d
        }

        rvMovieCast.layoutManager = createLayoutManager()
        rvMovieCast.adapter = mMovieCastAdapter

        var client = ResourceClient.Builder()
                .setRouter(ResourceRouterImpl.newInstance(null, "credits"))
                .setSpiceManager(getServiceSpiceManager()).build()
        client.find(Movie::class.java, mMovieId.toString())

        client = ResourceClient.Builder()
                .setRouter(ResourceRouterImpl.newInstance())
                .setSpiceManager(getServiceSpiceManager()).build()
        client.find(Movie::class.java, mMovieId.toString())

        client = ResourceClient.Builder()
                .setRouter(ResourceRouterImpl.newInstance(null, "images"))
                .setSpiceManager(getServiceSpiceManager()).build()
        client.find(Movie::class.java, mMovieId.toString())
    }

    fun onEvent(movie: Movie) {
        tvBarTitle.text = movie.title
        tvMovieOverview.text = movie.overview
    }

    fun onEvent(casts: Cast.CastList) {
        mCasts = casts.casts
    }

    fun onEvent(images: Image.PosterList) {
        mCoverImages = images.results
    }

    fun createHeaderView(): View {
        val headerView = layoutInflater.inflate(R.layout.list_header_movie_detail, null)
        tvMovieOverview = headerView.bindView<TextView>(R.id.tvMovieOverview)
        vpMovieCover = headerView.bindView<ViewPager>(R.id.vpMovieCover)
        vpMovieCover.adapter = mMovieCoverAdapter
        return headerView
    }

    fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    inner class MovieCoverImageAdapter : PagerAdapter() {

        var data = listOf<Image>()
            set (value) {
                data = value
                notifyDataSetChanged()
            }

        override fun getCount(): Int {
            return data.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any? {
            val viewPagerItem = LayoutInflater.from(container.context).inflate(R.layout.view_pager_item_movie_cover, container, false)
            val ivMovieCover = viewPagerItem.bindView<ImageView>(R.id.ivMovieCover)
            val tvMovieCoverVoteCount = viewPagerItem.bindView<TextView>(R.id.tvMovieCoverVoteCount)

            //bind views
            val image = mCoverImages[position]
            Picasso.with(container.context).load("https://image.tmdb.org/t/p/w500/" + image.filePath).into(ivMovieCover)
            tvMovieCoverVoteCount.text = image.voteCount.toString()

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
            mContext = container!!.context
            val view = LayoutInflater.from(mContext).inflate(R.layout.recycle_view_item_movie_cast, container, false)
            return MovieCastViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?, position: Int) {

            val cast = mCasts[position]
            val holder = viewHolder as MovieCastViewHolder
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w150/" + cast.profilePath)
                    .transform(CircularTransformation())
                    //.placeholder(R.drawable.ic_launcher)
                    .into(holder.ivCast)
            holder.tvCastName.text = cast.name
            holder.tvCastCharacter.text = "as " + cast.character
        }

        override fun getItemCount(): Int {
            return mCasts.size
        }

    }
}
