package com.taskworld.android.restfulandroidkotlin.fragments

import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import android.support.v4.view.ViewPager
import com.taskworld.android.restfulandroidkotlin.extensions.bindView
import android.view.View
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.events.OnToolbarTitleChangedEvent

/**
 * Created by Kittinun Vantasin on 11/7/14.
 */

class MovieFragment : BaseFragment() {

    override val mContentLayoutResourceId: Int = R.layout.fragment_movie

    //widgets
    val vpMovie by Delegates.lazy { getRootView()!!.bindView<ViewPager>(R.id.vpMovie) }

    //adapter
    val mMovieViewPagerAdapter by Delegates.lazy { MovieViewPagerAdapter(getFragmentManager()) }

    //data
    val mMovieCategories = listOf("now_playing", "top_rated")

    class object {
        fun newInstance(): MovieFragment {
            return MovieFragment()
        }
    }

    override fun setUp() {
    }

    override fun setUpUI(view: View?) {
        vpMovie.setAdapter(mMovieViewPagerAdapter)
        vpMovie.setOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                EventBus.getDefault().post(OnToolbarTitleChangedEvent(mMovieViewPagerAdapter.getPageTitle(position).toString()))
            }
        })
    }

    inner class MovieViewPagerAdapter(val fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return mMovieCategories.size
        }

        override fun getItem(position: Int): Fragment? {
            return MovieGridFragment.newInstance(mMovieCategories[position])
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mMovieCategories[position].toUpperCase()
        }
    }
}
