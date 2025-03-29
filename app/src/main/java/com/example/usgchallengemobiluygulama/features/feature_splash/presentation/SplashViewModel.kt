package com.example.usgchallengemobiluygulama.features.feature_splash.presentation

import androidx.lifecycle.viewModelScope
import com.example.usgchallengemobiluygulama.core.navigation.NavigationEvent
import com.example.usgchallengemobiluygulama.core.navigation.NavigationManager
import com.example.usgchallengemobiluygulama.core.navigation.Screen
import com.example.usgchallengemobiluygulama.core.presentation.util.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val navigationManager: NavigationManager
) : BaseViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        startSplashTimer()
    }

    private fun startSplashTimer() {
        viewModelScope.launch {
            delay(2000) // 2 saniye bekle
            navigateToHome()
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