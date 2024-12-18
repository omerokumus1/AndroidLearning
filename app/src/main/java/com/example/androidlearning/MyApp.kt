package com.example.androidlearning

import android.app.Application
import com.example.androidlearning.di.AppModule
import com.example.androidlearning.di.AppModuleImpl

class MyApp: Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}