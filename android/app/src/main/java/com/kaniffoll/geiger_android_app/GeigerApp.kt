package com.kaniffoll.geiger_android_app

import android.app.Application
import com.kaniffoll.geiger_android_app.di.appModule
import org.koin.core.context.startKoin

class GeigerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}