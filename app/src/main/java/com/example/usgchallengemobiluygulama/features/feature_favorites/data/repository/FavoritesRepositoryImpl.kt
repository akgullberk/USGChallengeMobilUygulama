package com.example.usgchallengemobiluygulama.features.feature_favorites.data.repository

import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.model.FavoriteLocation
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.repository.FavoritesRepository
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.City
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.Location
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val cityRepository: CityRepository
) : FavoritesRepository {
    private val favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    private val cityLocationMap = mutableMapOf<Int, Pair<String, Location>>()

    override fun getFavoriteLocations(): Flow<List<FavoriteLocation>> {
        return favoriteIds.map { ids ->
            ids.mapNotNull { id ->
                cityLocationMap[id]?.let { (cityName, location) ->
                    FavoriteLocation(
                        id = location.id,
                        name = location.name,
                        description = location.description,
                        coordinates = location.coordinates,
                        image = location.image,
                        city = cityName
                    )
                }
            }
        }
    }

    override suspend fun getFavoriteIds(): Set<Int> {
        return favoriteIds.value
    }

    override suspend fun addFavorite(locationId: Int, cityName: String) {
        favoriteIds.value = favoriteIds.value + locationId
        
        // Eğer bu konum daha önce kaydedilmemişse, konumu bul ve kaydet
        if (!cityLocationMap.containsKey(locationId)) {
            findAndCacheLocation(locationId, cityName)
        }
    }

    override suspend fun removeFavorite(locationId: Int) {
        favoriteIds.value = favoriteIds.value - locationId
    }

    override suspend fun isFavorite(locationId: Int): Boolean {
        return favoriteIds.value.contains(locationId)
    }
    
    private suspend fun findAndCacheLocation(locationId: Int, cityName: String) {
        // Tüm şehirleri çek ve bu ID'ye sahip konumu bul
        for (page in 1..4) { // API'nin 4 sayfası olduğunu biliyoruz
            val result = cityRepository.getCities(page)
            if (result is com.example.usgchallengemobiluygulama.core.domain.util.Result.Success) {
                for (city in result.data.data) {
                    for (location in city.locations) {
                        if (location.id == locationId) {
                            cityLocationMap[locationId] = Pair(city.city, location)
                            return
                        }
                    }
                }
            }
        }
    }
} 