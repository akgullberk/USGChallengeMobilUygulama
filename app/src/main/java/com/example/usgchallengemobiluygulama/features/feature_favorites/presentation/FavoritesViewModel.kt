package com.example.usgchallengemobiluygulama.features.feature_favorites.presentation

import androidx.lifecycle.viewModelScope
import com.example.usgchallengemobiluygulama.core.navigation.NavigationEvent
import com.example.usgchallengemobiluygulama.core.navigation.NavigationManager
import com.example.usgchallengemobiluygulama.core.navigation.Screen
import com.example.usgchallengemobiluygulama.core.presentation.util.BaseViewModel
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.usecase.GetFavoriteLocationsUseCase
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val navigationManager: NavigationManager,
    private val getFavoriteLocationsUseCase: GetFavoriteLocationsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        _state.value = state.value.copy(isLoading = true)
        getFavoriteLocationsUseCase().onEach { favorites ->
            _state.value = state.value.copy(
                favoriteLocations = favorites,
                isLoading = false
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: FavoritesAction) {
        when (action) {
            is FavoritesAction.ToggleFavorite -> toggleFavorite(action.locationId, action.cityName)
            is FavoritesAction.NavigateToDetail -> navigateToDetail(action.locationId)
            FavoritesAction.NavigateBack -> navigateBack()
        }
    }

    private fun toggleFavorite(locationId: Int, cityName: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(locationId, cityName)
        }
    }

    private fun navigateToDetail(locationId: Int) {
        viewModelScope.launch {
            navigationManager.navigate(
                NavigationEvent.NavigateTo(Screen.Detail.createRoute(locationId))
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            navigationManager.navigate(NavigationEvent.NavigateBack)
        }
    }
} 