package com.example.usgchallengemobiluygulama.features.feature_home.domain.repository

interface FavoritesRepository {
    suspend fun getFavorites(): Set<Int>
    suspend fun addFavorite(locationId: Int)
    suspend fun removeFavorite(locationId: Int)
    suspend fun isFavorite(locationId: Int): Boolean
} 