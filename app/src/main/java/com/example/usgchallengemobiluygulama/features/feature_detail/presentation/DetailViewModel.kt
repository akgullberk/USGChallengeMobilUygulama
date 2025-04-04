package com.example.usgchallengemobiluygulama.features.feature_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.core.navigation.NavigationEvent
import com.example.usgchallengemobiluygulama.core.navigation.NavigationManager
import com.example.usgchallengemobiluygulama.core.navigation.Screen
import com.example.usgchallengemobiluygulama.core.presentation.util.BaseViewModel
import com.example.usgchallengemobiluygulama.features.feature_detail.domain.usecase.GetLocationDetailUseCase
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.usecase.ToggleFavoriteUseCase
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val navigationManager: NavigationManager,
    private val getLocationDetailUseCase: GetLocationDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val favoritesRepository: FavoritesRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    
    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()
    
    private val locationId: Int = checkNotNull(savedStateHandle["locationId"])
    
    init {
        loadLocationDetail()
        checkIfFavorite()
    }
    
    fun onAction(action: DetailAction) {
        when (action) {
            DetailAction.ToggleFavorite -> toggleFavorite()
            DetailAction.NavigateBack -> navigateBack()
            DetailAction.ShowOnMap -> showOnMap()
        }
    }
    
    private fun loadLocationDetail() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true, error = null)
            
            when (val result = getLocationDetailUseCase(locationId)) {
                is Result.Success -> {
                    _state.value = state.value.copy(
                        locationDetail = result.data,
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    _state.value = state.value.copy(

                        isLoading = false
                    )
                }
                is Result.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }
    }
    
    private fun checkIfFavorite() {
        viewModelScope.launch {
            val isFavorite = favoritesRepository.isFavorite(locationId)
            _state.value = state.value.copy(isFavorite = isFavorite)
        }
    }
    
    private fun toggleFavorite() {
        viewModelScope.launch {
            val cityName = state.value.locationDetail?.cityName ?: return@launch
            val isFavorite = toggleFavoriteUseCase(locationId, cityName)
            _state.value = state.value.copy(isFavorite = isFavorite)
        }
    }
    
    private fun navigateBack() {
        viewModelScope.launch {
            navigationManager.navigate(NavigationEvent.NavigateBack)
        }
    }
    
    private fun showOnMap() {
        viewModelScope.launch {
            state.value.locationDetail?.let { detail ->
                navigationManager.navigate(
                    NavigationEvent.NavigateTo(Screen.LocationMap.createRoute(locationId))
                )
            }
        }
    }
} 