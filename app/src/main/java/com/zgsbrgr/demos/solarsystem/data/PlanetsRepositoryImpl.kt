package com.zgsbrgr.demos.solarsystem.data

import com.zgsbrgr.demos.solarsystem.domain.PlanetsRepository
import com.zgsbrgr.demos.solarsystem.domain.model.Planet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlanetsRepositoryImpl: PlanetsRepository {

    override suspend fun loadPlanet(which: Int): Result<Planet> = withContext(Dispatchers.IO) {
        return@withContext try {

            val planet = orderedPlanetsList[which]
            Result.Success(planet)

        }catch (e: Exception) {
            Result.Error(IllegalArgumentException("Planet not found"))
        }
    }

    override suspend fun getAllPlanets(): Result<List<Planet>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val planetList = orderedPlanetsList
            Result.Success(planetList)
        }catch (e: Exception) {
            Result.Error(IllegalStateException())
        }
    }


}