package com.example.usgchallengemobiluygulama.features.feature_favorites.domain.model

import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.Coordinates

data class FavoriteLocation(
    val id: Int,
    val name: String,
    val description: String,
    val coordinates: Coordinates,
    val image: String?,
    val city: String
) 