package com.example.usgchallengemobiluygulama.features.feature_detail.domain.repository

import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.features.feature_detail.domain.model.LocationDetail

interface LocationDetailRepository {
    suspend fun getLocationDetail(locationId: Int): Result<LocationDetail>
} 