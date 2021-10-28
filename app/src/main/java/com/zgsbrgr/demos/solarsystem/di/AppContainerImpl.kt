package com.zgsbrgr.demos.solarsystem.di

import android.content.Context
import com.zgsbrgr.demos.solarsystem.data.PlanetsRepositoryImpl
import com.zgsbrgr.demos.solarsystem.domain.PlanetsRepository


class AppContainerImpl(private val applicationContext: Context): AppContainer {

    override val planetsRepository: PlanetsRepository by lazy {
        PlanetsRepositoryImpl()
    }

}