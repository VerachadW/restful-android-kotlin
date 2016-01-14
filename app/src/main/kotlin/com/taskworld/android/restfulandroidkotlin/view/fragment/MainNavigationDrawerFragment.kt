package com.taskworld.android.restfulandroidkotlin.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.octo.android.robospice.SpiceManager
import com.squareup.picasso.Picasso
import com.taskworld.android.restfulandroidkotlin.R
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import com.taskworld.android.restfulandroidkotlin.extension.toast
import com.taskworld.android.restfulandroidkotlin.network.service.TheMovieAPISpiceService
import com.taskworld.android.restfulandroidkotlin.util.Preference
import kotlinx.android.synthetic.main.fragment_main_navigation_drawer.*
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 11/5/14.
 */

class MainNavigationDrawerFragment : BaseDrawerFragment() {
    override val mContentLayoutResourceId: Int
        get() = throw UnsupportedOperationException()

    val mSpiceManager: SpiceManager = SpiceManager(TheMovieAPISpiceService::class.java)

    //widgets
    var tvAccountName: TextView by Delegates.notNull()

    //data
    var mCurrentSelectedPosition = 0

    override fun setUp() {
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_main_navigation_drawer, container, false)

        var lvMainNavigation = view.findViewById(R.id.lvMainNavigation) as ListView
        lvMainNavigation.addHeaderView(createHeaderView())

        lvMainNavigation.adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_activated_1, android.R.id.text1, listOf("Movies", "TVs"))

        lvMainNavigation.setItemChecked(1, true)
        lvMainNavigation.setOnItemClickListener { parent, view, position, id ->
            val ft = fragmentManager.beginTransaction()
            when (position) {
                1 -> ft.replace(R.id.flContainer, MovieFragment.newInstance())
                2 -> ft.replace(R.id.flContainer, TVFragment.newInstance())
            }
            ft.commit()
            selectItem(position)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        mSpiceManager.start(activity)
    }

    override fun onStop() {
        super.onStop()
        mSpiceManager.shouldStop()
    }

    fun selectItem(position: Int) {
        mCurrentSelectedPosition = position
        lvMainNavigation.setItemChecked(position, true)
        closeDrawer()
    }

    override fun onDrawerClosed() {

    }

    override fun onDrawerOpened() {

    }

    fun createHeaderView(): View {
        val llNavigationHeader = LayoutInflater.from(activity).inflate(R.layout.list_header_main_navigation, null, false)

        val ivAccountCover = llNavigationHeader.bindView<ImageView>(R.id.ivAccountCover)
        Picasso.with(activity).load("http://2.bp.blogspot.com/-6oNTuKj2y1I/VDW1uaZUu3I/AAAAAAAALDc/tJM0s5p1-5o/s1600/Untitled-1.jpg").into(ivAccountCover)

        tvAccountName = llNavigationHeader.bindView<TextView>(R.id.tvAccountName)
        tvAccountName.text = Preference.with(activity).username ?: "N/A"

        val ibConfig = llNavigationHeader.bindView<ImageButton>(R.id.ibAccountConfig)
        ibConfig.setOnClickListener { view ->
            toast("configure")
        }

        return llNavigationHeader
    }
}

