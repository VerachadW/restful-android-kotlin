package com.taskworld.android.restfulandroidkotlin.view.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.event.OnMovieCategorySelectedEvent
import com.taskworld.android.restfulandroidkotlin.event.OnToolbarTitleChangedEvent
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import com.taskworld.android.restfulandroidkotlin.model.Movie
import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient
import com.taskworld.android.restfulandroidkotlin.resource.router.ResourceRouterImpl
import com.taskworld.android.restfulandroidkotlin.view.activity.MovieDetailActivity
import de.greenrobot.event.EventBus
import io.realm.Realm
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 11/6/14.
 */
class MovieGridFragment : BaseSpiceFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_movie_grid

    //adapter
    val mMovieAdapter = MovieRecycleViewAdapter()

    //data
    var mItems by Delegates.observable(arrayListOf<Movie>(), { meta, oldItems, newItems ->
        mMovieAdapter.notifyDataSetChanged()
    })
    var mCategory = ""
    val mBus = EventBus()

    companion object {
        val ARG_MOVIE_CATEGORY = "movie_category"

        fun newInstance(action: String): MovieGridFragment {
            val fragment = MovieGridFragment()
            val args = Bundle()
            args.putString(ARG_MOVIE_CATEGORY, action)
            fragment.arguments = args
            return fragment
        }
    }

    override fun setUp() {
        setHasOptionsMenu(true)
        mBus.register(this)
    }

    override fun setUpUI(view: View) {
        val rvMovie = getRootView().bindView<RecyclerView>(R.id.rvMovie)
        rvMovie.layoutManager = createLayoutManager()
        rvMovie.addItemDecoration(createItemDecoration())
        rvMovie.adapter = mMovieAdapter

        val client = ResourceClient.Builder()
                .setRouter(ResourceRouterImpl.newInstance(mCategory))
                .setRealm(Realm.getInstance(activity))
                .setEventBus(mBus)
                .setSpiceManager(getServiceSpiceManager()).build()

        client.findAll(Movie::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_grid_movie, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.miSearch -> {
            }
            R.id.miAdd -> addMovie()
            R.id.miRemove -> removeMovie()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        mBus.unregister(this)
        super.onDestroy()
    }

    override fun handleArguments(args: Bundle) {
        mCategory = args.getString(ARG_MOVIE_CATEGORY)
    }

    fun onEvent(items: Movie.ResultList) {
        mItems = items.results.toArrayList()
    }

    fun onEvent(event: OnMovieCategorySelectedEvent) {
        mCategory = event.category
        EventBus.getDefault().post(OnToolbarTitleChangedEvent(mCategory.toUpperCase()))
    }

    fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    fun createItemDecoration(): RecyclerView.ItemDecoration {
        return InsetDecoration()
    }

    fun addMovie() {
        var m = Movie()

        m.title = "My new movie"
        m.posterPath = "/aJemoN7F9GrAYjIL94KXww3QWP9.jpg"
        m.popularity = (Math.random() * 100).toFloat()

        mItems.add(1, m)
        mMovieAdapter.notifyItemInserted(1)
    }

    fun removeMovie() {
        mItems.removeAt(0)
        mMovieAdapter.notifyItemRemoved(0)
    }

    fun onItemClick(position: Int) {
        val movie = mItems[position]
        startActivity(MovieDetailActivity.newIntent(activity, movie.id))
    }

    inner class MovieRecycleViewAdapter : RecyclerView.Adapter<MovieRecycleViewAdapter.MovieViewHolder>() {

        override fun onCreateViewHolder(container: ViewGroup?, viewType: Int): MovieViewHolder? {
            val view = LayoutInflater.from(container?.context).inflate(R.layout.recycle_view_item_movie, container, false)
            return MovieViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
            val m = mItems[position]
            viewHolder.tvMovieTitle.text = m.title
            viewHolder.tvMoviePopularScore.text = m.popularity.toString()
            Picasso.with(activity).load("https://image.tmdb.org/t/p/w500/" + m.posterPath).into(viewHolder.ivMovieCover)
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            init {
                itemView.setOnClickListener { view -> onItemClick(adapterPosition) }
            }

            val tvMovieTitle = itemView.bindView<TextView>(R.id.tvMovieTitle)
            val tvMoviePopularScore = itemView.bindView<TextView>(R.id.tvMoviePopularScore)
            val ivMovieCover = itemView.bindView<ImageView>(R.id.ivMovieCover)
        }
    }

    inner class InsetDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            outRect!!.set(8, 8, 8, 8)
        }
    }
}