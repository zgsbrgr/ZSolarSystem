package com.zgsbrgr.demos.solarsystem.ui.planet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.zgsbrgr.demos.solarsystem.data.Result
import com.zgsbrgr.demos.solarsystem.domain.PlanetsRepository
import com.zgsbrgr.demos.solarsystem.domain.model.Planet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlanetUiState(
    val planet: Planet? = null,
    val loading: Boolean = false
) {
    val loadingFailed: Boolean
        get() = !loading && planet == null
}

class PlanetDetailViewModel(
    private val planetsRepository: PlanetsRepository
): ViewModel() {


    private val _uiState = MutableStateFlow(PlanetUiState(loading = true))
    val uiState: StateFlow<PlanetUiState> = _uiState.asStateFlow()

    private val currentPlanetPosition = mutableStateOf(0)


    init {
        viewModelScope.launch {
            val response = planetsRepository.getAllPlanets()
            _uiState.update {
                when(response) {
                    is Result.Success -> it.copy(planet = response.data[2], loading = false)
                    is Result.Error -> it.copy(loading = false)
                }
            }
        }
    }

    fun loadSinglePlanetByPosition(position: Int) {
        _uiState.update {
            it.copy(loading = true)
        }
        viewModelScope.launch {
            val response = planetsRepository.loadPlanet(position)
            _uiState.update {
                when(response) {
                    is Result.Success -> it.copy(planet = response.data, loading = false)
                    is Result.Error -> {
                        currentPlanetPosition.value = 0
                        it.copy(loading = false)
                    }
                }
            }
        }
    }

    fun loadNextPlanet() {
        loadSinglePlanetByPosition(currentPlanetPosition.value++)
    }


    companion object {

        fun provideFactory(
            planetsRepository: PlanetsRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return PlanetDetailViewModel(planetsRepository) as T
                }
            }
    }
}