package com.example.usgchallengemobiluygulama.features.feature_location.domain.repository

import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.features.feature_location.domain.model.MapLocation

interface LocationMapRepository {
    suspend fun getLocationForMap(locationId: Int): Result<MapLocation>
} 