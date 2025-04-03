package com.example.usgchallengemobiluygulama.features.feature_splash.data.repository

import com.example.usgchallengemobiluygulama.core.domain.util.NetworkError
import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.CityResponse
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.repository.CityRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class CityRepositoryImpl(
    private val client: HttpClient
) : CityRepository {
    override suspend fun getCities(page: Int): Result<CityResponse> {
        return try {
            val response = client.get("https://storage.googleapis.com/invio-com/usg-challenge/city-location/page-$page.json")
            Result.Success(response.body())
        } catch (e: Exception) {
            Result.Error(NetworkError.UnknownError(e.message ?: "Bilinmeyen bir hata oluştu"))
        }
    }
} 