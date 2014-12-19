package com.taskworld.android.restfulandroidkotlin.fragments.dialog

import android.support.v4.app.DialogFragment
import android.os.Bundle
import android.app.Dialog
import android.app.AlertDialog
import de.greenrobot.event.EventBus
import com.taskworld.android.restfulandroidkotlin.model.FavoriteList
import com.taskworld.android.restfulandroidkotlin.R
import kotlin.properties.Delegates
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import android.widget.EditText
import com.taskworld.android.restfulandroidkotlin.network.RestfulResourceClient

/**
 * Created by VerachadW on 11/17/14.
 */
class CreatePlayListDialogFragment(val client: RestfulResourceClient): DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog? {

        val etName = EditText(getActivity())

        val builder = AlertDialog.Builder(getActivity())

        builder.setTitle("Enter new Playlist")
               .setView(etName)
            .setPositiveButton("OK", {(interface, view) ->
                
            }).setNegativeButton("Cancel", {(interface, view) -> interface.dismiss()})

        return builder.create()
    }

}