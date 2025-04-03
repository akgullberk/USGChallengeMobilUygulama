package com.example.usgchallengemobiluygulama.features.feature_splash.presentation

import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.City

data class SplashState(
    val isLoading: Boolean = true,
    val cities: List<City> = emptyList()
) 