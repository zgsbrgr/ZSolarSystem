package com.zgsbrgr.demos.solarsystem.ui.planet

import androidx.lifecycle.ViewModel
import com.zgsbrgr.demos.solarsystem.domain.PlanetsRepository
import com.zgsbrgr.demos.solarsystem.domain.model.Planet

data class PlanetListUiState(
    val planets: List<Planet> = emptyList(),
    val loading: Boolean = false
)

class PlanetListViewModel(
    val planetsRepository: PlanetsRepository
): ViewModel() {


}