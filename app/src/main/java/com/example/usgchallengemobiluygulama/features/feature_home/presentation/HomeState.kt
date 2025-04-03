package com.example.usgchallengemobiluygulama.features.feature_home.presentation

import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.City

data class HomeState(
    val cities: List<City> = emptyList(),
    val expandedCities: Set<String> = emptySet(),
    val favoritedLocations: Set<Int> = emptySet(),
    val isLoading: Boolean = false,
    val currentPage: Int = 1,
    val hasMorePages: Boolean = true
) 