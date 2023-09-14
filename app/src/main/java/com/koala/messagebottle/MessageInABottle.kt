package com.koala.messagebottle

import android.app.Application
import android.content.pm.ApplicationInfo
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber.*
import timber.log.Timber.Forest.plant


@HiltAndroidApp
class MessageInABottle : Application() {
    override fun onCreate() {
        super.onCreate()

        // setup the appropriate Timber tree
        val isDebuggableBuild = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        if (isDebuggableBuild) {
            plant(DebugTree())
        } else {
            // TODO: plugin production CrashReportingTree
            //plant(CrashReportingTree())
        }
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            //TODO: plug in crashlytics
//            FakeCrashLibrary.log(priority, tag, message)
//            if (t != null) {
//                if (priority == Log.ERROR) {
//                    FakeCrashLibrary.logError(t)
//                } else if (priority == Log.WARN) {
//                    FakeCrashLibrary.logWarning(t)
//                }
//            }
        }
    }
}

