package com.taskworld.android.restfulandroidkotlin.activities

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.fragments.MainNavigationDrawerFragment
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.Button
import com.taskworld.android.restfulandroidkotlin.model.Movie
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.tag
import com.taskworld.android.restfulandroidkotlin.network.request.GetTokenSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.response.EventBusRequestListener
import com.taskworld.android.restfulandroidkotlin.network.request.ValidateTokenSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.request.GetNewSessionSpiceRequest
import com.taskworld.android.restfulandroidkotlin.Preference
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import com.taskworld.android.restfulandroidkotlin.fragments.MovieGridFragment
import com.taskworld.android.restfulandroidkotlin.fragments.BaseDrawerFragment

class MainActivity : BaseSpiceActivity() {

    override val mContentLayoutResourceId = R.layout.activity_main

    //widgets
    val fgLeftNavigationDrawer by Delegates.lazy {
        getSupportFragmentManager().findFragmentById(R.id.fgLeftNavigationDrawer) as BaseDrawerFragment
    }

    val fgRightNavigationDrawer by Delegates.lazy {
        getSupportFragmentManager().findFragmentById(R.id.fgRightNavigationDrawer) as BaseDrawerFragment
    }

    val dlMain by Delegates.lazy { bindView<DrawerLayout>(R.id.dlMain) }
    val tbMain by Delegates.lazy { bindView<Toolbar>(R.id.tbMain) }

    override fun setUp() {
        setSupportActionBar(tbMain)
        fgLeftNavigationDrawer.setUpAsLeftDrawer(dlMain, tbMain)
        fgRightNavigationDrawer.setUpAsRightDrawer(dlMain, tbMain)

        val ft = getSupportFragmentManager().beginTransaction()
        ft.replace(R.id.flContainer, MovieGridFragment.newInstance("popular"))
        ft.commit()
    }

    public fun onEvent(map: Map<String, String>) {
        if (map.contains("expires_at")) {
            getServiceSpiceManager().execute(ValidateTokenSpiceRequest("twmobile", "abcd1234", map.get("request_token")!!), EventBusRequestListener())
        } else if (map.contains("session_id")) {
            Preference.getInstance(this).setSessionId(map.get("session_id")!!)
        } else {
            getServiceSpiceManager().execute(GetNewSessionSpiceRequest(map.get("request_token")!!), EventBusRequestListener())
        }
    }
}