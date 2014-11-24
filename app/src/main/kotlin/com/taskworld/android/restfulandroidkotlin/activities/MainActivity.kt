package com.taskworld.android.restfulandroidkotlin.activities

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.Button
import com.taskworld.android.restfulandroidkotlin.model.Movie
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.tag
import com.taskworld.android.restfulandroidkotlin.network.request.GetTokenSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.response.EventBusRequestListener
import com.taskworld.android.restfulandroidkotlin.network.request.ValidateTokenSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.request.GetNewSessionSpiceRequest
import com.taskworld.android.restfulandroidkotlin.utils.Preference
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import com.taskworld.android.restfulandroidkotlin.fragments.BaseDrawerFragment
import com.taskworld.android.restfulandroidkotlin.events.OnDrawerToggledEvent
import com.taskworld.android.restfulandroidkotlin.fragments.BaseDrawerFragment.Direction
import android.widget.TextView
import com.taskworld.android.restfulandroidkotlin.events.OnToolbarTitleChangedEvent
import com.taskworld.android.restfulandroidkotlin.fragments.MovieFragment

class MainActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId = R.layout.activity_main

    //widgets
    val tvBarTitle by Delegates.lazy { bindView<TextView>(R.id.tvBarTitle) }

    val fgLeftNavigationDrawer by Delegates.lazy {
        getSupportFragmentManager().findFragmentById(R.id.fgLeftNavigationDrawer) as BaseDrawerFragment
    }

    val dlMain by Delegates.lazy { bindView<DrawerLayout>(R.id.dlMain) }
    val tbMain by Delegates.lazy { bindView<Toolbar>(R.id.tbMain) }

    override fun setUp() {
        setSupportActionBar(tbMain)
        fgLeftNavigationDrawer.setUpAsLeftDrawer(dlMain, tbMain)

        val popularCategory = "popular"
        tvBarTitle.setText(popularCategory.toUpperCase())
        val ft = getSupportFragmentManager().beginTransaction()
        ft.replace(R.id.flContainer, MovieFragment.newInstance())
        ft.commit()
    }


    fun onEvent(event: OnDrawerToggledEvent) {
        when (event.direction) {
            Direction.LEFT -> fgLeftNavigationDrawer.toggleDrawer()
        }
    }

    fun onEvent(event: OnToolbarTitleChangedEvent) {
        tvBarTitle.setText(event.title)
    }
}