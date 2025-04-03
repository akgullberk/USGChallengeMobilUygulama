package com.example.usgchallengemobiluygulama.features.feature_splash.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(
    val currentPage: Int,
    val totalPage: Int,
    val total: Int,
    val itemPerPage: Int,
    val pageSize: Int,
    val data: List<City>
)

@Serializable
data class City(
    val city: String,
    val locations: List<Location>
)

@Serializable
data class Location(
    val name: String,
    val description: String,
    val coordinates: Coordinates,
    val image: String?,
    val id: Int
)

@Serializable
data class Coordinates(
    val lat: Double,
    val lng: Double
) 