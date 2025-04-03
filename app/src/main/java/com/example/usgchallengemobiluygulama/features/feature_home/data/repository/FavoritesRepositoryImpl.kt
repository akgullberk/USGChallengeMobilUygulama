package com.example.usgchallengemobiluygulama.features.feature_home.data.repository

import com.example.usgchallengemobiluygulama.features.feature_home.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesRepositoryImpl : FavoritesRepository {
    private val favorites = MutableStateFlow<Set<Int>>(emptySet())

    override suspend fun getFavorites(): Set<Int> {
        return favorites.value
    }

    override suspend fun addFavorite(locationId: Int) {
        favorites.value = favorites.value + locationId
    }

    override suspend fun removeFavorite(locationId: Int) {
        favorites.value = favorites.value - locationId
    }

    override suspend fun isFavorite(locationId: Int): Boolean {
        return favorites.value.contains(locationId)
    }
} 