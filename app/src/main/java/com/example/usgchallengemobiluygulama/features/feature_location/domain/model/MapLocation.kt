package com.example.usgchallengemobiluygulama.features.feature_location.domain.model

import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.Coordinates

data class MapLocation(
    val id: Int,
    val name: String,
    val coordinates: Coordinates,
    val cityName: String
) 