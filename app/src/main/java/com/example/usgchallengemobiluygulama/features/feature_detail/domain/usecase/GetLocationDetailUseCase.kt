package com.example.usgchallengemobiluygulama.features.feature_detail.domain.usecase

import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.features.feature_detail.domain.model.LocationDetail
import com.example.usgchallengemobiluygulama.features.feature_detail.domain.repository.LocationDetailRepository

class GetLocationDetailUseCase(private val repository: LocationDetailRepository) {
    suspend operator fun invoke(locationId: Int): Result<LocationDetail> {
        return repository.getLocationDetail(locationId)
    }
} 