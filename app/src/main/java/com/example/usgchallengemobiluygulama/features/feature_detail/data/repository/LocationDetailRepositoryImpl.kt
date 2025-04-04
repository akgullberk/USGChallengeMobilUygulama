package com.example.usgchallengemobiluygulama.features.feature_detail.data.repository

import com.example.usgchallengemobiluygulama.core.domain.util.NetworkError
import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.features.feature_detail.domain.model.LocationDetail
import com.example.usgchallengemobiluygulama.features.feature_detail.domain.repository.LocationDetailRepository
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.repository.CityRepository

class LocationDetailRepositoryImpl(
    private val cityRepository: CityRepository
) : LocationDetailRepository {

    override suspend fun getLocationDetail(locationId: Int): Result<LocationDetail> {
        // Tüm şehirleri çek ve bu ID'ye sahip konumu bul
        for (page in 1..4) { // API'nin 4 sayfası olduğunu biliyoruz
            val result = cityRepository.getCities(page)
            if (result is Result.Success) {
                for (city in result.data.data) {
                    for (location in city.locations) {
                        if (location.id == locationId) {
                            return Result.Success(
                                LocationDetail(
                                    id = location.id,
                                    name = location.name,
                                    description = location.description,
                                    coordinates = location.coordinates,
                                    image = location.image,
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
        return Result.Error(NetworkError.ServerError)
    }
}