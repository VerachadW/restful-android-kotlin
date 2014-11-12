package com.taskworld.android.restfulandroidkotlin.fragments

import android.view.View
import kotlin.properties.Delegates
import android.widget.ListView
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.widget.ArrayAdapter
import com.taskworld.android.restfulandroidkotlin.R
import android.view.LayoutInflater
import android.widget.ImageView
import com.squareup.picasso.Picasso
import android.widget.TextView
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extensions.tag
import android.widget.ImageButton
import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.service.TheMovieAPISpiceService
import com.taskworld.android.restfulandroidkotlin.network.request.ValidateTokenSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.response.EventBusRequestListener
import com.taskworld.android.restfulandroidkotlin.network.request.GetNewSessionSpiceRequest
import com.taskworld.android.restfulandroidkotlin.network.request.GetTokenSpiceRequest

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

class MainNavigationDrawerFragment : BaseDrawerFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_main_navigation_drawer

    val mSpiceManager: SpiceManager = SpiceManager(javaClass<TheMovieAPISpiceService>())

    //widgets
    val lvDrawer by Delegates.lazy { getRootView().bindView<ListView>(R.id.lvMainNavigation) }
    var tvAccountName: TextView by Delegates.notNull()

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

    override fun onStart() {
        super<BaseDrawerFragment>.onStart()
        mSpiceManager.start(getActivity())
    }

    override fun onStop() {
        super<BaseDrawerFragment>.onStop()
        mSpiceManager.shouldStop()
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

        val ivAccountCover = llNavigationHeader.bindView<ImageView>(R.id.ivAccountCover)
        Picasso.with(getActivity()).load("http://2.bp.blogspot.com/-6oNTuKj2y1I/VDW1uaZUu3I/AAAAAAAALDc/tJM0s5p1-5o/s1600/Untitled-1.jpg").into(ivAccountCover)

        tvAccountName = llNavigationHeader.bindView<TextView>(R.id.tvAccountName)
        tvAccountName.setText("Tap at gear icon to login")

        val ibConfig = llNavigationHeader.bindView<ImageButton>(R.id.ibAccountConfig)
        ibConfig.setOnClickListener { view ->
            mSpiceManager.execute(GetTokenSpiceRequest(), EventBusRequestListener.newInstance())
        }

        return llNavigationHeader
    }

    public fun onEvent(map: Map<String, String>) {
        val accountName = "twmobile"
        val accountPassword = "abcd1234"

        if (map.contains("expires_at")) {
            mSpiceManager.execute(ValidateTokenSpiceRequest(accountName, accountPassword, map.get("request_token")!!), EventBusRequestListener.newInstance())
        } else if (map.contains("session_id")) {
            tvAccountName.setText(accountName)
            com.taskworld.android.restfulandroidkotlin.utils.Preference.getInstance(getActivity()).setSessionId(map.get("session_id")!!)
        } else {
            mSpiceManager.execute(GetNewSessionSpiceRequest(map.get("request_token")!!), EventBusRequestListener.newInstance())
        }
    }
}

