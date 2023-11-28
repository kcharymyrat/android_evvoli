package com.example.evvolitm

import android.app.Application
import com.example.evvolitm.data.AppContainer
import com.example.evvolitm.data.DefaultAppContainer

class EvvoliTmApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}