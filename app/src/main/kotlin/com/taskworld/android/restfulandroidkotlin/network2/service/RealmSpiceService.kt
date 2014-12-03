package com.taskworld.android.restfulandroidkotlin.network2.service

import com.octo.android.robospice.SpiceService
import android.app.Application
import com.octo.android.robospice.persistence.CacheManager
import com.octo.android.robospice.persistence.retrofit.GsonRetrofitObjectPersisterFactory
import com.octo.android.robospice.networkstate.NetworkStateChecker
import android.content.Context

/**
 * Created by VerachadW on 11/26/14.
 */

class RealmSpiceService(): SpiceService() {
    override fun createCacheManager(application: Application?): CacheManager? {
        val cacheManager = CacheManager()
        return cacheManager
    }

    override fun getNetworkStateChecker(): NetworkStateChecker? {
        return OfflineNetworkStateChecker()
    }

    inner class OfflineNetworkStateChecker: NetworkStateChecker {
        override fun isNetworkAvailable(context: Context?): Boolean {
            return true
        }

        override fun checkPermissions(context: Context?) {
        }
    }
}

