package com.example.usgchallengemobiluygulama.features.feature_splash.domain.repository

import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.CityResponse

interface CityRepository {
    suspend fun getCities(page: Int): Result<CityResponse>
} 