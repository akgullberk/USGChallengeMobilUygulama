package com.example.usgchallengemobiluygulama.features.feature_favorites.domain.repository

import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.model.FavoriteLocation
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoriteLocations(): Flow<List<FavoriteLocation>>
    suspend fun getFavoriteIds(): Set<Int>
    suspend fun addFavorite(locationId: Int, cityName: String)
    suspend fun removeFavorite(locationId: Int)
    suspend fun isFavorite(locationId: Int): Boolean
} 