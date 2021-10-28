package com.zgsbrgr.demos.solarsystem.di

import com.zgsbrgr.demos.solarsystem.domain.PlanetsRepository

interface AppContainer {

    val planetsRepository: PlanetsRepository

}