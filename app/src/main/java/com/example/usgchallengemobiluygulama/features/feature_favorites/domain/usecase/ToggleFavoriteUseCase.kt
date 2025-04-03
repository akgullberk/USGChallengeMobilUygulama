package com.example.usgchallengemobiluygulama.features.feature_favorites.domain.usecase

import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.repository.FavoritesRepository

class ToggleFavoriteUseCase(private val repository: FavoritesRepository) {
    suspend operator fun invoke(locationId: Int, cityName: String): Boolean {
        return if (repository.isFavorite(locationId)) {
            repository.removeFavorite(locationId)
            false
        } else {
            repository.addFavorite(locationId, cityName)
            true
        }
    }
} 