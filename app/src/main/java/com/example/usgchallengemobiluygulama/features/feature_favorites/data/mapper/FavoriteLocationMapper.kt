package com.example.usgchallengemobiluygulama.features.feature_favorites.data.mapper

import com.example.usgchallengemobiluygulama.features.feature_favorites.data.local.entity.FavoriteLocationEntity
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.model.FavoriteLocation
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.Coordinates
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.Location

fun FavoriteLocationEntity.toDomainModel(): FavoriteLocation {
    return FavoriteLocation(
        id = id,
        name = name,
        description = description,
        coordinates = Coordinates(latitude, longitude),
        image = image,
        city = cityName
    )
}

fun Location.toFavoriteLocationEntity(cityName: String): FavoriteLocationEntity {
    return FavoriteLocationEntity(
        id = id,
        name = name,
        description = description,
        latitude = coordinates.lat,
        longitude = coordinates.lng,
        image = image,
        cityName = cityName
    )
} 