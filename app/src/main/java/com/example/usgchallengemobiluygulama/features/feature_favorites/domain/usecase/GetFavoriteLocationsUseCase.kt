package com.example.usgchallengemobiluygulama.features.feature_favorites.domain.usecase

import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.model.FavoriteLocation
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteLocationsUseCase(private val repository: FavoritesRepository) {
    operator fun invoke(): Flow<List<FavoriteLocation>> {
        return repository.getFavoriteLocations()
    }
} 