package com.example.usgchallengemobiluygulama.features.feature_home.domain.usecase

import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.CityResponse
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.repository.CityRepository

class GetCitiesPageUseCase(private val repository: CityRepository) {
    suspend operator fun invoke(page: Int): Result<CityResponse> {
        return repository.getCities(page)
    }
} 