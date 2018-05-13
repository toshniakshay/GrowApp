package com.android.growappdemo.Application

import android.app.Application

/**
 * Application class for getting application context to non-activity classes
 */
class GApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}