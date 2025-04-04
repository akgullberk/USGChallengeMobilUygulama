package com.example.usgchallengemobiluygulama.features.feature_location.domain.usecase

import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.features.feature_location.domain.model.MapLocation
import com.example.usgchallengemobiluygulama.features.feature_location.domain.repository.LocationMapRepository

class GetLocationForMapUseCase(private val repository: LocationMapRepository) {
    suspend operator fun invoke(locationId: Int): Result<MapLocation> {
        return repository.getLocationForMap(locationId)
    }
} 