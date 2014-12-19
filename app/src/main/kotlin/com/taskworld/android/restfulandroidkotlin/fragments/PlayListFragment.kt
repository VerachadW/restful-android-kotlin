package com.taskworld.android.restfulandroidkotlin.fragments

import com.taskworld.android.restfulandroidkotlin.R
import android.view.View
import android.support.v7.widget.RecyclerView
import kotlin.properties.Delegates
import com.taskworld.android.restfulandroidkotlin.fragments.PlayListFragment.PlayListRecyclerViewAdapter.PlayListViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import com.taskworld.android.restfulandroidkotlin.model.FavoriteList
import io.realm.Realm
import java.util.ArrayList
import de.greenrobot.event.EventBus
import io.realm.RealmResults

import com.taskworld.android.restfulandroidkotlin.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.taskworld.android.restfulandroidkotlin.fragments.dialog.CreatePlayListDialogFragment
import com.taskworld.android.restfulandroidkotlin.view.fragment.BaseSpiceFragment
import com.taskworld.android.restfulandroidkotlin.extension.bindView
import com.taskworld.android.restfulandroidkotlin.event.OnToolbarTitleChangedEvent

/**
 * Created by VerachadW on 11/12/14.
 */
class PlayListFragment() : BaseSpiceFragment() {
    override val mContentLayoutResourceId: Int = R.layout.fragment_playlist

    val rvFavorite by Delegates.lazy { getRootView().bindView<RecyclerView>(R.id.rvPlayList) }

    val mPlayListAdapter by Delegates.lazy { PlayListRecyclerViewAdapter() }

    val mBus = EventBus()

    class object {
        public fun newInstance(): PlayListFragment{
           return PlayListFragment()
        }
    }

    override fun setUp() {
        mBus.register(this)
        setHasOptionsMenu(true)
    }

    override fun onDestroy(){
        mBus.unregister(this)
        super<BaseSpiceFragment>.onDestroy();
    }

    override fun setUpUI(view: View) {

        EventBus.getDefault().post(OnToolbarTitleChangedEvent("PlayList"))

        rvFavorite.setLayoutManager(createLayoutManager())
        rvFavorite.setAdapter(mPlayListAdapter)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_playlist, menu)
        super<BaseSpiceFragment>.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.getItemId()) {
            R.id.action_crate_playlist -> openCreatePlayListDialog()
        }
        return super<BaseSpiceFragment>.onOptionsItemSelected(item)
    }

    fun createLayoutManager(): RecyclerView.LayoutManager{
       return LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
    }

    inner class PlayListRecyclerViewAdapter : RecyclerView.Adapter<PlayListViewHolder>() {

        private var mItems by Delegates.observable(arrayListOf<FavoriteList>(), {meta, oldItems, newItems ->
            notifyDataSetChanged()
        })

        public fun removePlayList(position: Int) {
            mItems.remove(position)
            notifyItemRemoved(position)
        }

        public fun addPlayList(playList: FavoriteList) {
            mItems.add(0, playList)
            notifyItemInserted(0)
        }

        public fun addAll(items: ArrayList<FavoriteList>) {
            mItems = items
        }

        override fun onCreateViewHolder(container: ViewGroup?, p1: Int): PlayListViewHolder? {
            val view = LayoutInflater.from(container?.getContext()).inflate(R.layout.recycle_view_item_playlist, container, false)
            return PlayListViewHolder(view)
        }

        override fun onBindViewHolder(vh: PlayListViewHolder?, position: Int) {
            vh!!.tvName.setText(mItems[position].getName())
        }


        override fun getItemCount(): Int {
            return mItems.size
        }

        inner class PlayListViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val tvName: TextView by Delegates.lazy { view.bindView<TextView>(R.id.tvPlayListName)  }
        }
    }

    fun openCreatePlayListDialog() {
    }

}
