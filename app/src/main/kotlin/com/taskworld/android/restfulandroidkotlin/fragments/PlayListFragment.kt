package com.taskworld.android.restfulandroidkotlin.fragments

import com.taskworld.android.restfulandroidkotlin.R
import android.view.View
import android.support.v7.widget.RecyclerView
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.bindView

/**
 * Created by VerachadW on 11/12/14.
 */
class PlayListFragment() : BaseSpiceFragment() {
    override val mContentLayoutResourceId: Int = R.layout.fragment_playlist

    val rvFavorite: RecyclerView by Delegates.lazy { getActivity().bindView<RecyclerView>(R.id.rvPlayList) }

    class object {
        public fun newInstance(): PlayListFragment{
           return PlayListFragment()
        }
    }

    override fun setUpUI(view: View) {

    }

}
