package com.zgsbrgr.demos.solarsystem.ui.planet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zgsbrgr.demos.solarsystem.data.Result
import com.zgsbrgr.demos.solarsystem.domain.PlanetsRepository
import com.zgsbrgr.demos.solarsystem.domain.model.Planet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlanetListUiState(
    val planets: List<Planet> = emptyList(),
    val selected: Int = 3,
    val loading: Boolean = false
)

class PlanetListViewModel(
    private val planetsRepository: PlanetsRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(PlanetListUiState(loading = true))
    val uiState: StateFlow<PlanetListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val response = planetsRepository.getAllPlanets()
            _uiState.update {
                when(response) {
                    is Result.Success -> it.copy(planets = response.data, loading = false)
                    is Result.Error -> it.copy(loading = false)
                }
            }
        }
    }

    fun changeSelection(position: Int) {
        _uiState.update {
            it.copy(selected = position)
        }
    }

    companion object {

        fun provideFactory(
            planetsRepository: PlanetsRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return PlanetListViewModel(planetsRepository) as T
                }
            }
    }

}