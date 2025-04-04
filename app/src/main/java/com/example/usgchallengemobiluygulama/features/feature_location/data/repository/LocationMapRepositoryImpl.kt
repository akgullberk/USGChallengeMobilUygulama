package com.example.usgchallengemobiluygulama.features.feature_location.data.repository

import com.example.usgchallengemobiluygulama.core.domain.util.NetworkError
import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.features.feature_location.domain.model.MapLocation
import com.example.usgchallengemobiluygulama.features.feature_location.domain.repository.LocationMapRepository
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.repository.CityRepository

class LocationMapRepositoryImpl(
    private val cityRepository: CityRepository
) : LocationMapRepository {
    
    override suspend fun getLocationForMap(locationId: Int): Result<MapLocation> {
        // Tüm şehirleri çek ve bu ID'ye sahip konumu bul
        for (page in 1..4) { // API'nin 4 sayfası olduğunu biliyoruz
            val result = cityRepository.getCities(page)
            if (result is Result.Success) {
                for (city in result.data.data) {
                    for (location in city.locations) {
                        if (location.id == locationId) {
                            return Result.Success(
                                MapLocation(
                                    id = location.id,
                                    name = location.name,
                                    coordinates = location.coordinates,
                                    cityName = city.city
                                )
                            )
                        }
                    }
                }
            } else if (result is Result.Error) {
                return Result.Error(result.exception)
            }
        }
        return Result.Error(NetworkError.NotFound("Konum bulunamadı"))
    }
} 