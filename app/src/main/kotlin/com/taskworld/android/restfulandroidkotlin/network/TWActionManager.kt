package com.taskworld.android.restfulandroidkotlin.network

import com.octo.android.robospice.SpiceManager
import com.taskworld.android.restfulandroidkotlin.network.action.GetMovieFeedAction
import com.taskworld.android.restfulandroidkotlin.network.action.GetMovieFeedActionExecutor

/**
 * Created by Johnny Dew on 11/12/2014 AD.
 */

class TWActionManager(var spiceManager: SpiceManager) {

    fun execute(action: BaseAction) {

        //TODO put asynchronous action into pending pool

        ActionExecutor.createActionExecutor(action, spiceManager)?.execute()
    }

}

abstract class ActionExecutor(var action: BaseAction?, var spiceManager: SpiceManager) {

    abstract fun execute()

    class object {
        fun createActionExecutor(action: BaseAction, spiceManager: SpiceManager): ActionExecutor?{
            var actionExecutor: ActionExecutor? = null
            when (action) {
                is GetMovieFeedAction -> actionExecutor = GetMovieFeedActionExecutor(action, spiceManager)
            }

            return actionExecutor
        }
    }

}

open class BaseAction {

}

open class BaseReaction {

}




