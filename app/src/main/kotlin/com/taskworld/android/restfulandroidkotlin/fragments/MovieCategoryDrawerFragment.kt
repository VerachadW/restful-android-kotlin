package com.taskworld.android.restfulandroidkotlin.fragments

import com.taskworld.android.restfulandroidkotlin.R
import android.widget.ListView
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import kotlin.properties.Delegates
import android.view.View
import android.widget.ArrayAdapter
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.events.OnMovieCategorySelectedEvent

/**
 * Created by Kittinun Vantasin on 11/6/14.
 */

class MovieCategoryDrawerFragment : BaseDrawerFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_movie_category_drawer

    //widgets
    val lvDrawer by Delegates.lazy { getRootView().bindView<ListView>(R.id.lvMovieCategory) }

    //data
    var mCurrentSelectedPosition = 0

    override fun setUp() {
    }

    override fun setUpUI(view: View) {
        val categories = listOf("popular", "upcoming", "now_playing")

        lvDrawer.setAdapter(ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                categories))

        setSelectedItem(0)

        lvDrawer.setOnItemClickListener { (adapterView, view, position, id) ->
            setSelectedItem(position)
            EventBus.getDefault().post(OnMovieCategorySelectedEvent(categories[position]))
        }
    }

    fun setSelectedItem(position: Int) {
        mCurrentSelectedPosition = position
        lvDrawer.setItemChecked(position, true)
        closeDrawer()
    }
}
