package com.example.usgchallengemobiluygulama.features.feature_home.domain.usecase

import com.example.usgchallengemobiluygulama.features.feature_home.domain.repository.FavoritesRepository

class ToggleFavoriteUseCase(private val repository: FavoritesRepository) {
    suspend operator fun invoke(locationId: Int): Boolean {
        return if (repository.isFavorite(locationId)) {
            repository.removeFavorite(locationId)
            false
        } else {
            repository.addFavorite(locationId)
            true
        }
    }
} 