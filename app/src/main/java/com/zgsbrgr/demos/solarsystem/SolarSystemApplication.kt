package com.zgsbrgr.demos.solarsystem

import android.app.Application
import com.zgsbrgr.demos.solarsystem.di.AppContainer
import com.zgsbrgr.demos.solarsystem.di.AppContainerImpl

class SolarSystemApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }

}