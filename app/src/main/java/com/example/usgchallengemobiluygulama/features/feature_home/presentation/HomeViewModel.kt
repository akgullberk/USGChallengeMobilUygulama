package com.example.usgchallengemobiluygulama.features.feature_home.presentation

import androidx.lifecycle.viewModelScope
import com.example.usgchallengemobiluygulama.core.navigation.NavigationEvent
import com.example.usgchallengemobiluygulama.core.navigation.NavigationManager
import com.example.usgchallengemobiluygulama.core.navigation.Screen
import com.example.usgchallengemobiluygulama.core.presentation.util.BaseViewModel
import com.example.usgchallengemobiluygulama.features.feature_home.domain.usecase.GetCitiesPageUseCase
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.usecase.ToggleFavoriteUseCase
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.usgchallengemobiluygulama.core.domain.util.Result

class HomeViewModel(
    private val navigationManager: NavigationManager,
    private val getCitiesPageUseCase: GetCitiesPageUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val favoritesRepository: FavoritesRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadNextPage() // İlk sayfayı otomatik yükle
        loadFavorites() // Favorileri yükle
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ToggleCityExpansion -> toggleCityExpansion(action.cityName)
            is HomeAction.ToggleLocationFavorite -> toggleLocationFavorite(action.locationId, action.cityName)
            HomeAction.CollapseAllCities -> collapseAllCities()
            HomeAction.LoadNextPage -> loadNextPage()
            HomeAction.NavigateToFavorites -> navigateToFavorites()
            is HomeAction.NavigateToDetail -> navigateToDetail(action.locationId)
        }
    }

    private fun toggleCityExpansion(cityName: String) {
        _state.value = state.value.let { currentState ->
            if (currentState.expandedCities.contains(cityName)) {
                currentState.copy(expandedCities = currentState.expandedCities - cityName)
            } else {
                currentState.copy(expandedCities = currentState.expandedCities + cityName)
            }
        }
    }

    private fun toggleLocationFavorite(locationId: Int, cityName: String) {
        viewModelScope.launch {
            val isFavorite = toggleFavoriteUseCase(locationId, cityName)
            _state.value = state.value.let { currentState ->
                if (isFavorite) {
                    currentState.copy(favoritedLocations = currentState.favoritedLocations + locationId)
                } else {
                    currentState.copy(favoritedLocations = currentState.favoritedLocations - locationId)
                }
            }
        }
    }

    private fun collapseAllCities() {
        _state.value = state.value.copy(expandedCities = emptySet())
    }

    private fun loadNextPage() {
        if (state.value.isLoading || !state.value.hasMorePages) return

        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            
            when (val result = getCitiesPageUseCase(state.value.currentPage)) {
                is Result.Success -> {
                    val response = result.data
                    _state.value = state.value.copy(
                        cities = state.value.cities + response.data,
                        currentPage = response.currentPage + 1,
                        hasMorePages = response.currentPage < response.totalPage,
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    handleError(result.exception)
                    _state.value = state.value.copy(isLoading = false)
                }
                else -> Unit
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            val favoriteIds = favoritesRepository.getFavoriteIds()
            _state.value = state.value.copy(
                favoritedLocations = favoriteIds
            )
        }
    }

    private fun navigateToFavorites() {
        viewModelScope.launch {
            navigationManager.navigate(NavigationEvent.NavigateTo(Screen.Favorites.route))
        }
    }

    private fun navigateToDetail(locationId: Int) {
        viewModelScope.launch {
            navigationManager.navigate(
                NavigationEvent.NavigateTo(Screen.Detail.createRoute(locationId))
            )
        }
    }
} 