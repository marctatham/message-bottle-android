package com.koala.messagebottle

import android.app.Application
import android.content.pm.ApplicationInfo
import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
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
            plant(CrashReportingTree())
        }
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            Firebase.crashlytics.log("${priority.logPriorityPrefix}: $message")
            t?.let { Firebase.crashlytics.recordException(it) }
        }
    }
}

private val Int.logPriorityPrefix: String
    get() {
        return when (this) {
            Log.DEBUG -> "d"
            Log.VERBOSE -> "v"
            Log.INFO -> "i"
            Log.ERROR -> "e"
            Log.WARN -> "w"
            Log.ASSERT -> "a"
            else -> "undefined"
        }
    }
