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

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

class MainNavigationDrawerFragment : BaseDrawerFragment() {

    var mCurrentSelectedPosition = 0

    //widgets
    val lvDrawer by Delegates.lazy { getRootView()!!.bindView<ListView>(R.id.lvMainNavigation) }

    override val mContentLayoutResourceId: Int = R.layout.fragment_main_navigation_drawer

    override fun setUp() {
    }

    override fun setUpUI(view: View?) {
        lvDrawer.setOnItemClickListener { parent, view, position, id ->
            selectItem(position)
            when (position) {
                0 -> startActivity(MovieListActivity.newInstance(getActivity()))
                1 -> startActivity(ProductListActivity.newInstance(getActivity()))
            }
        }

        lvDrawer.setAdapter(ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                listOf("Movie List", "Product List")))
    }

    fun selectItem(position: Int) {
        mCurrentSelectedPosition = position
        closeDrawer()
    }

    override fun onDrawerClosed() {
        toast(getString(R.string.navigation_drawer_close))
    }

    override fun onDrawerOpened() {
        toast(getString(R.string.navigation_drawer_open))
    }
}

