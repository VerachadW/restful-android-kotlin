package com.taskworld.android.restfulandroidkotlin.fragments

import android.view.View
import kotlin.properties.Delegates
import android.widget.ListView
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.ArrayAdapter
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.activities.MovieListActivity
import com.taskworld.android.restfulandroidkotlin.activities.ProductListActivity
import com.taskworld.android.restfulandroidkotlin.extensions.toast
import android.view.LayoutInflater
import android.widget.ImageView
import com.squareup.picasso.Picasso
import android.widget.TextView
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.tag

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

class MainNavigationDrawerFragment : BaseDrawerFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_main_navigation_drawer

    //widgets
    val lvDrawer by Delegates.lazy { getRootView().bindView<ListView>(R.id.lvMainNavigation) }

    //data
    var mCurrentSelectedPosition = 0

    override fun setUp() {
    }

    override fun setUpUI(view: View) {
        lvDrawer.addHeaderView(createHeaderView())

        lvDrawer.setAdapter(ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                listOf("Movies", "TVs")))

        lvDrawer.setItemChecked(1, true)
        lvDrawer.setOnItemClickListener { parent, view, position, id ->
            val ft = getFragmentManager().beginTransaction()
            when (position) {
                1 -> ft.replace(R.id.flContainer, MovieFragment.newInstance())
                2 -> ft.replace(R.id.flContainer, TVFragment.newInstance())
            }
            ft.commit()
            selectItem(position)
        }
    }

    fun selectItem(position: Int) {
        mCurrentSelectedPosition = position
        lvDrawer.setItemChecked(position, true)
        closeDrawer()
    }

    override fun onDrawerClosed() {
        Log.v(tag(), getString(R.string.navigation_drawer_close))
    }

    override fun onDrawerOpened() {
        Log.v(tag(), getString(R.string.navigation_drawer_open))
    }

    fun createHeaderView(): View {
        val llNavigationHeader = LayoutInflater.from(getActivity()).inflate(R.layout.list_header_main_navigation, null, false)

        val ivNavigationCover = llNavigationHeader.bindView<ImageView>(R.id.ivNavigationCover)
        Picasso.with(getActivity()).load("http://2.bp.blogspot.com/-6oNTuKj2y1I/VDW1uaZUu3I/AAAAAAAALDc/tJM0s5p1-5o/s1600/Untitled-1.jpg").into(ivNavigationCover)

        val tvNavigationName = llNavigationHeader.bindView<TextView>(R.id.tvNavigationName)
        tvNavigationName.setText("john.doe@themoviedb.org")
        return llNavigationHeader
    }
}

