package com.koala.messagebottle

import android.app.Application
import android.content.pm.ApplicationInfo
import com.koala.messagebottle.common.performance.CrashReportingTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant


@HiltAndroidApp
class BottleCastApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // setup the appropriate Timber tree based on whether or not application is debuggable
        val isDebuggableBuild = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        if (isDebuggableBuild) {
            plant(DebugTree())
        } else {
            plant(CrashReportingTree())
        }
    }
}
