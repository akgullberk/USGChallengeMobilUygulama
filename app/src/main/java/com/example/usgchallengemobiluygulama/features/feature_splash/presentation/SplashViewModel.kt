package com.example.usgchallengemobiluygulama.features.feature_splash.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.usgchallengemobiluygulama.core.navigation.NavigationEvent
import com.example.usgchallengemobiluygulama.core.navigation.NavigationManager
import com.example.usgchallengemobiluygulama.core.navigation.Screen
import com.example.usgchallengemobiluygulama.core.presentation.util.BaseViewModel
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.usecase.GetInitialCitiesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.usgchallengemobiluygulama.core.domain.util.Result

class SplashViewModel(
    private val navigationManager: NavigationManager,
    private val getInitialCitiesUseCase: GetInitialCitiesUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        fetchCities()
    }

    private fun fetchCities() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            when (val result = getInitialCitiesUseCase()) {
                is Result.Success -> {
                    Log.d("SplashViewModel", "Cities: ${result.data}")
                    _state.value = state.value.copy(
                        isLoading = false,
                        cities = result.data.data
                    )
                    navigateToHome()
                }
                is Result.Error -> {
                    _state.value = state.value.copy(isLoading = false)
                    handleError(result.exception)
                }
                else -> Unit
            }
        }
    }

    private fun navigateToHome() {
        viewModelScope.launch {
            navigationManager.navigate(
                NavigationEvent.NavigateToWithPopUp(
                    route = Screen.Home.route,
                    popUpTo = Screen.Splash.route,
                    inclusive = true
                )
            )
        }
    }

    fun onAction(action: SplashAction) {
        when (action) {
            SplashAction.OnSplashFinished -> {
                _state.value = state.value.copy(isLoading = false)
            }
            SplashAction.NavigateToMain -> {
                navigateToHome()
            }
        }
    }
} 