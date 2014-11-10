package com.taskworld.android.restfulandroidkotlin.fragments

import com.taskworld.android.restfulandroidkotlin.R
import android.os.Bundle
import android.view.View
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import de.greenrobot.event.EventBus
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.taskworld.android.restfulandroidkotlin.model.TV
import android.widget.TextView
import android.widget.ImageView
import com.taskworld.android.restfulandroidkotlin.network.resource.client.ResourceClient
import com.taskworld.android.restfulandroidkotlin.network.resource.router.ResourceRouterImpl
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
import android.support.v7.widget.GridLayoutManager
import java.text.SimpleDateFormat

/**
 * Created by Kittinun Vantasin on 11/7/14.
 */

class TVGridFragment : BaseSpiceFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_tv_grid

    //widgets
    val rvTV by Delegates.lazy { getRootView().bindView<RecyclerView>(R.id.rvTV) }

    //adapter
    val mTVAdapter by Delegates.lazy { TVRecyclerViewAdapter() }

    //data
    var mItems by Delegates.observable(arrayListOf<TV>(), { meta, oldItems, newItems ->
        mTVAdapter.notifyDataSetChanged()
    })
    var mCategory = ""
    val mBus = EventBus()

    class object {
        val ARG_TV_CATEGORY = "tv_category"

        fun newInstance(category: String): TVGridFragment {
            val fragment = TVGridFragment()
            var args = Bundle()
            args.putString(ARG_TV_CATEGORY, category)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun setUp() {
        mBus.register(this)
    }

    override fun setUpUI(view: View) {
        rvTV.setLayoutManager(createLayoutManager())
        //set adapter
        rvTV.setAdapter(mTVAdapter)

        val client = ResourceClient.Builder()
                .setRouter(ResourceRouterImpl.newInstance(mCategory))
                .setEventBus(mBus)
                .setSpiceManager(getServiceSpiceManager()).build()

        client.findAll(javaClass<TV>())
    }

    override fun handleArguments(args: Bundle) {
        mCategory = args.getString(ARG_TV_CATEGORY)
    }

    fun onEvent(items: TV.ResultList) {
        mItems = items.getResults().toArrayList()
    }

    fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(getActivity(), 2)
    }

    override fun onDestroy() {
        mBus.unregister(this)
        super<BaseSpiceFragment>.onDestroy()
    }

    inner class TVRecyclerViewAdapter : RecyclerView.Adapter<TVRecyclerViewAdapter.TVViewHolder>() {

        override fun onCreateViewHolder(container: ViewGroup?, position: Int): TVViewHolder? {
            val view = LayoutInflater.from(container?.getContext()).inflate(R.layout.recycle_view_item_tv, container, false)
            return TVViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: TVViewHolder, position: Int) {
            val tv = mItems[position]
            viewHolder.tvTVTitle.setText(tv.getName())
            val formatter = SimpleDateFormat("dd MMM yyyy")
            viewHolder.tvTVFirstAirDate.setText(formatter.format(tv.getFirstAirDate()))
            Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w500/" + tv.getPosterPath()).into(viewHolder.ivTVCover)
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        inner class TVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvTVTitle by Delegates.lazy { itemView.bindView<TextView>(R.id.tvTVTitle) }
            val tvTVFirstAirDate by Delegates.lazy { itemView.bindView<TextView>(R.id.tvTVFirstAirDate) }
            val ivTVCover by Delegates.lazy { itemView.bindView<ImageView>(R.id.ivTVCover) }
        }
    }

}
