package com.example.usgchallengemobiluygulama.features.feature_favorites.presentation

sealed interface FavoritesAction {
    data class ToggleFavorite(val locationId: Int, val cityName: String) : FavoritesAction
    data class NavigateToDetail(val locationId: Int) : FavoritesAction
    object NavigateBack : FavoritesAction
} 