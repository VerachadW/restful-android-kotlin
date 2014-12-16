package com.taskworld.android.restfulandroidkotlin.view.fragment

import com.taskworld.android.restfulandroidkotlin.R
import android.view.View
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import android.view.LayoutInflater
import com.taskworld.android.restfulandroidkotlin.model.Movie
import android.support.v7.widget.LinearLayoutManager
import android.os.Bundle
import com.squareup.picasso.Picasso
import android.graphics.Rect
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.event.OnMovieCategorySelectedEvent
import com.taskworld.android.restfulandroidkotlin.event.OnToolbarTitleChangedEvent
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extension.tag
import com.taskworld.android.restfulandroidkotlin.view.activity.MovieDetailActivity
import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient
import com.taskworld.android.restfulandroidkotlin.resource.router.ResourceRouterImpl
import io.realm.Realm
import com.taskworld.android.restfulandroidkotlin.network.RestfulResourceClient
import com.taskworld.android.restfulandroidkotlin.network.request.GetMoviesLocalRequest
import com.taskworld.android.restfulandroidkotlin.network.OnMovieLoadedEvent
import com.taskworld.android.restfulandroidkotlin.network.request.GetMoviesRequest

/**
 * Created by Kittinun Vantasin on 11/6/14.
 */

class MovieGridFragment : BaseSpiceFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_movie_grid

    //widgets
    val rvMovie by Delegates.lazy { getRootView().bindView<RecyclerView>(R.id.rvMovie) }

    //adapter
    val mMovieAdapter by Delegates.lazy { MovieRecycleViewAdapter() }

    //data
    var mItems by Delegates.observable(arrayListOf<Movie>(), { meta, oldItems, newItems ->
        mMovieAdapter.notifyDataSetChanged()
    })
    var mCategory = ""
    val mBus = EventBus()

    class object {
        val ARG_MOVIE_CATEGORY = "movie_category"

        fun newInstance(action: String): MovieGridFragment {
            val fragment = MovieGridFragment()
            val args = Bundle()
            args.putString(ARG_MOVIE_CATEGORY, action)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun setUp() {
        setHasOptionsMenu(true)
        mBus.register(this)
    }

    override fun setUpUI(view: View) {
        rvMovie.setLayoutManager(createLayoutManager())
        rvMovie.addItemDecoration(createItemDecoration())
        //set adapter
        rvMovie.setAdapter(mMovieAdapter)

        val request = GetMoviesRequest(Realm.getInstance(getActivity()), mCategory)
        val restClient = RestfulResourceClient.newInstance(getServiceSpiceManager(), getLocalSpiceManager(), mBus)
        restClient.execute(request)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_grid_movie, menu)
        super<BaseSpiceFragment>.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.miSearch -> Log.i(tag(), "search")
            R.id.miAdd -> addMovie()
            R.id.miRemove -> removeMovie()
        }
        return super<BaseSpiceFragment>.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        mBus.unregister(this)
        super<BaseSpiceFragment>.onDestroy()
    }

    override fun handleArguments(args: Bundle) {
        mCategory = args.getString(ARG_MOVIE_CATEGORY)
    }

    fun onEvent(event: OnMovieLoadedEvent) {
        mItems = event.result.getResults().toArrayList()
        log("MovieResults-> Id: ${event.requestId} Source: ${event.source.toString()} Action: ${event.action.toString()}")
    }


    fun onEvent(event: OnMovieCategorySelectedEvent) {
        mCategory = event.category
        EventBus.getDefault().post(OnToolbarTitleChangedEvent(mCategory.toUpperCase()))
    }

    fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
    }

    fun createItemDecoration(): RecyclerView.ItemDecoration {
        return InsetDecoration()
    }

    fun addMovie() {
        var m = Movie()

        m.setTitle("My new movie")
        m.setPosterPath("/aJemoN7F9GrAYjIL94KXww3QWP9.jpg")
        m.setPopularity((Math.random() * 100).toFloat())

        mItems.add(1, m)
        mMovieAdapter.notifyItemInserted(1)
    }

    fun removeMovie() {
        mItems.remove(0)
        mMovieAdapter.notifyItemRemoved(0)
    }

    fun onItemClick(position: Int) {
        val movie = mItems[position]
        startActivity(MovieDetailActivity.newIntent(getActivity(), movie.getId()))
    }

    inner class MovieRecycleViewAdapter : RecyclerView.Adapter<MovieRecycleViewAdapter.MovieViewHolder>() {

        override fun onCreateViewHolder(container: ViewGroup?, viewType: Int): MovieViewHolder? {
            val view = LayoutInflater.from(container?.getContext()).inflate(R.layout.recycle_view_item_movie, container, false)
            return MovieViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
            val m = mItems[position]
            viewHolder.tvMovieTitle.setText(m.getTitle())
            viewHolder.tvMoviePopularScore.setText(m.getPopularity().toString())
            Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w500/" + m.getPosterPath()).into(viewHolder.ivMovieCover)
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            {
                itemView.setOnClickListener { view -> onItemClick(getPosition()) }
            }

            val tvMovieTitle by Delegates.lazy { itemView.bindView<TextView>(R.id.tvMovieTitle) }
            val tvMoviePopularScore by Delegates.lazy { itemView.bindView<TextView>(R.id.tvMoviePopularScore) }
            val ivMovieCover by Delegates.lazy { itemView.bindView<ImageView>(R.id.ivMovieCover) }
        }
    }

    inner class InsetDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            outRect!!.set(8, 8, 8, 8)
        }
    }
}