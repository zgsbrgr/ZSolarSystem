package com.zgsbrgr.demos.solarsystem.domain

import com.zgsbrgr.demos.solarsystem.data.Result
import com.zgsbrgr.demos.solarsystem.domain.model.Planet

interface PlanetsRepository {

    suspend fun loadPlanet(which: Int): Result<Planet>

    suspend fun getAllPlanets(): Result<List<Planet>>

}