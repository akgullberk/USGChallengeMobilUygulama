package com.example.usgchallengemobiluygulama.features.feature_home.presentation

sealed interface HomeAction {
    data class ToggleCityExpansion(val cityName: String) : HomeAction
    data class ToggleLocationFavorite(val locationId: Int, val cityName: String) : HomeAction
    object CollapseAllCities : HomeAction
    object LoadNextPage : HomeAction
    object NavigateToFavorites : HomeAction
    data class NavigateToDetail(val locationId: Int) : HomeAction
} 