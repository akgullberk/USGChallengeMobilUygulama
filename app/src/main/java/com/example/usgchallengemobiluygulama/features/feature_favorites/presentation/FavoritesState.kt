package com.example.usgchallengemobiluygulama.features.feature_favorites.presentation

import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.model.FavoriteLocation

data class FavoritesState(
    val favoriteLocations: List<FavoriteLocation> = emptyList(),
    val isLoading: Boolean = false
) 