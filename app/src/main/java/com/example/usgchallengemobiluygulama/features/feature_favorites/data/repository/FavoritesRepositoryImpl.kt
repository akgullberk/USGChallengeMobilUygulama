package com.example.usgchallengemobiluygulama.features.feature_favorites.data.repository

import com.example.usgchallengemobiluygulama.features.feature_favorites.data.local.dao.FavoriteLocationDao
import com.example.usgchallengemobiluygulama.features.feature_favorites.data.mapper.toFavoriteLocationEntity
import com.example.usgchallengemobiluygulama.features.feature_favorites.data.mapper.toDomainModel
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.model.FavoriteLocation
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.repository.FavoritesRepository
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.usgchallengemobiluygulama.core.domain.util.Result

class FavoritesRepositoryImpl(
    private val favoriteLocationDao: FavoriteLocationDao,
    private val cityRepository: CityRepository
) : FavoritesRepository {

    override fun getFavoriteLocations(): Flow<List<FavoriteLocation>> {
        return favoriteLocationDao.getAllFavorites().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getFavoriteIds(): Set<Int> {
        return favoriteLocationDao.getAllFavoriteIds().toSet()
    }

    override suspend fun addFavorite(locationId: Int, cityName: String) {
        val location = findLocation(locationId, cityName)
        location?.let {
            favoriteLocationDao.insertFavorite(it.toFavoriteLocationEntity(cityName))
        }
    }

    override suspend fun removeFavorite(locationId: Int) {
        favoriteLocationDao.deleteFavorite(locationId)
    }

    override suspend fun isFavorite(locationId: Int): Boolean {
        return favoriteLocationDao.isFavorite(locationId)
    }
    
    private suspend fun findLocation(locationId: Int, cityName: String): com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.Location? {
        // Tüm şehirleri çek ve bu ID'ye sahip konumu bul
        for (page in 1..4) { // API'nin 4 sayfası olduğunu biliyoruz
            val result = cityRepository.getCities(page)
            if (result is Result.Success) {
                for (city in result.data.data) {
                    for (location in city.locations) {
                        if (location.id == locationId) {
                            return location
                        }
                    }
                }
            }
        }
        return null
    }
} 