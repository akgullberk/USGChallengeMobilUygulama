package com.example.usgchallengemobiluygulama.features.feature_splash.domain.usecase

import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.CityResponse
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.repository.CityRepository

class GetInitialCitiesUseCase(private val repository: CityRepository) {
    suspend operator fun invoke(): Result<CityResponse> {
        return repository.getCities(1)
    }
} 