package com.zachcervi.smack_android.Controller

import Utilities.SharedPrefs
import android.app.Application

class App: Application() {

    companion object {
        lateinit var prefs: SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}