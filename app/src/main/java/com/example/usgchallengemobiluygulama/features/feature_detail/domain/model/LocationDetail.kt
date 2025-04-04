package com.example.usgchallengemobiluygulama.features.feature_detail.domain.model

import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.Coordinates

data class LocationDetail(
    val id: Int,
    val name: String,
    val description: String,
    val coordinates: Coordinates,
    val image: String?,
    val cityName: String
) 