package com.taskworld.android.restfulandroidkotlin.view.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import com.taskworld.android.restfulandroidkotlin.extension.toast
import com.taskworld.android.restfulandroidkotlin.model.TV
import com.taskworld.android.restfulandroidkotlin.resource.client.ResourceClient
import com.taskworld.android.restfulandroidkotlin.resource.router.ResourceRouterImpl
import de.greenrobot.event.EventBus
import io.realm.Realm
import java.text.SimpleDateFormat
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 11/7/14.
 */
class TVGridFragment : BaseSpiceFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_tv_grid

    //adapter
    val mTVAdapter = TVRecyclerViewAdapter()

    //data
    var mItems by Delegates.observable(arrayListOf<TV>(), { meta, oldItems, newItems ->
        mTVAdapter.notifyDataSetChanged()
    })
    var mCategory = ""
    val mBus = EventBus()

    companion object {
        val ARG_TV_CATEGORY = "tv_category"

        fun newInstance(category: String): TVGridFragment {
            val fragment = TVGridFragment()
            var args = Bundle()
            args.putString(ARG_TV_CATEGORY, category)
            fragment.arguments = args
            return fragment
        }
    }

    override fun setUp() {
        mBus.register(this)
    }

    override fun setUpUI(view: View) {
        val rvTV = getRootView().bindView<RecyclerView>(R.id.rvTV)
        rvTV.layoutManager = createLayoutManager()
        rvTV.adapter = mTVAdapter

        val client = ResourceClient.Builder()
                .setRouter(ResourceRouterImpl.newInstance(mCategory))
                .setRealm(Realm.getInstance(activity))
                .setEventBus(mBus)
                .setSpiceManager(getServiceSpiceManager()).build()

        client.findAll(TV::class.java)
    }

    override fun handleArguments(args: Bundle) {
        mCategory = args.getString(ARG_TV_CATEGORY)
    }

    fun onEvent(items: TV.ResultList) {
        mItems = items.results.toArrayList()
    }

    fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(activity, 2)
    }

    override fun onDestroy() {
        mBus.unregister(this)
        super.onDestroy()
    }

    fun onItemClick(position: Int) {
        val tv = mItems[position]
        toast(tv.name)
    }

    inner class TVRecyclerViewAdapter : RecyclerView.Adapter<TVRecyclerViewAdapter.TVViewHolder>() {

        override fun onCreateViewHolder(container: ViewGroup?, position: Int): TVViewHolder? {
            val view = LayoutInflater.from(container?.context).inflate(R.layout.recycle_view_item_tv, container, false)
            return TVViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: TVViewHolder, position: Int) {
            val tv = mItems[position]
            viewHolder.tvTVTitle.text = tv.name
            val formatter = SimpleDateFormat("dd MMM yyyy")
            viewHolder.tvTVFirstAirDate.text = formatter.format(tv.firstAirDate)
            Picasso.with(activity).load("https://image.tmdb.org/t/p/w500/" + tv.posterPath).into(viewHolder.ivTVCover)
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        inner class TVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            init {
                itemView.setOnClickListener { view -> onItemClick(adapterPosition) }
            }

            val tvTVTitle = itemView.bindView<TextView>(R.id.tvTVTitle)
            val tvTVFirstAirDate = itemView.bindView<TextView>(R.id.tvTVFirstAirDate)
            val ivTVCover = itemView.bindView<ImageView>(R.id.ivTVCover)
        }
    }
}
