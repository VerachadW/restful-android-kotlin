package com.taskworld.android.restfulandroidkotlin.view.fragment

import android.view.View
import kotlin.properties.Delegates
import android.widget.ListView
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import android.widget.ArrayAdapter
import com.taskworld.android.restfulandroidkotlin.R
import android.view.LayoutInflater
import android.widget.ImageView
import com.squareup.picasso.Picasso
import android.widget.TextView
import android.util.Log
import com.taskworld.android.restfulandroidkotlin.extension.tag
import android.widget.ImageButton
import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.service.TheMovieAPISpiceService
import com.taskworld.android.restfulandroidkotlin.extension.toast
import com.taskworld.android.restfulandroidkotlin.util.Preference
import com.taskworld.android.restfulandroidkotlin.network.RestfulResourceClient
import com.taskworld.android.restfulandroidkotlin.network.request.GetTokenRequest
import com.taskworld.android.restfulandroidkotlin.network.OnDataReceivedEvent
import com.taskworld.android.restfulandroidkotlin.network.request.OnAuthenSuccessEvent
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.network.request.ValidateTokenRequest
import com.taskworld.android.restfulandroidkotlin.network.request.GetNewSessionRequest

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

class MainNavigationDrawerFragment : BaseDrawerFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_main_navigation_drawer

    //widgets
    val lvDrawer by Delegates.lazy { getRootView().bindView<ListView>(R.id.lvMainNavigation) }
    var tvAccountName: TextView by Delegates.notNull()

    //data
    var mCurrentSelectedPosition = 0

    val mRestClient = RestfulResourceClient.newInstance(getServiceSpiceManager(), getLocalSpiceManager(), EventBus.getDefault())

    override fun setUp() {
    }

    override fun setUpUI(view: View) {
        lvDrawer.addHeaderView(createHeaderView())

        lvDrawer.setAdapter(ArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                listOf("Movies", "TVs", "PlayLists")))

        lvDrawer.setItemChecked(1, true)
        lvDrawer.setOnItemClickListener { parent, view, position, id ->
            val ft = getFragmentManager().beginTransaction()
            when (position) {
                1 -> ft.replace(R.id.flContainer, MovieFragment.newInstance())
                2 -> ft.replace(R.id.flContainer, TVFragment.newInstance())
                3 -> ft.replace(R.id.flContainer, PlayListFragment.newInstance())
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

        val ivAccountCover = llNavigationHeader.bindView<ImageView>(R.id.ivAccountCover)
        Picasso.with(getActivity()).load("http://2.bp.blogspot.com/-6oNTuKj2y1I/VDW1uaZUu3I/AAAAAAAALDc/tJM0s5p1-5o/s1600/Untitled-1.jpg").into(ivAccountCover)

        tvAccountName = llNavigationHeader.bindView<TextView>(R.id.tvAccountName)
        tvAccountName.setText(Preference.with(getActivity()).username ?: "N/A")

        val ibConfig = llNavigationHeader.bindView<ImageButton>(R.id.ibAccountConfig)
        ibConfig.setOnClickListener { view ->
            mRestClient.execute(GetTokenRequest())
        }

        return llNavigationHeader
    }

    public fun onEvent(event: OnAuthenSuccessEvent) {
        val accountName = "twmobile"
        val accountPassword = "abcd1234"

        if (event.result.contains("expires_at")) {
            mRestClient.execute(ValidateTokenRequest(accountName, accountPassword, event.result.get("request_token")!!))
        } else if (event.result.contains("session_id")) {
            tvAccountName.setText(accountName)
            com.taskworld.android.restfulandroidkotlin.utils.Preference.getInstance(getActivity()).setSessionId(event.result.get("session_id")!!)
        } else {
            mRestClient.execute(GetNewSessionRequest(event.result.get("request_token")!!))
        }
    }
}

