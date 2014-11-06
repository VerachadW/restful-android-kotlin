package com.taskworld.android.restfulandroidkotlin.fragments

import com.taskworld.android.restfulandroidkotlin.R
import android.widget.ListView
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import kotlin.properties.Delegates
import android.view.View
import android.widget.ArrayAdapter

/**
 * Created by Kittinun Vantasin on 11/6/14.
 */

class MovieCategoryDrawerFragment : BaseDrawerFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_movie_category_drawer

    //widgets
    val lvDrawer by Delegates.lazy { getRootView()!!.bindView<ListView>(R.id.lvMovieCategory) }

    override fun setUp() {
    }

    override fun setUpUI(view: View?) {
        lvDrawer.setAdapter(ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                listOf("latest", "upcoming", "now_playing", "popular")))
    }
}
