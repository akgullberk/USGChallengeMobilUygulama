package com.example.usgchallengemobiluygulama.features.feature_favorites.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_locations")
data class FavoriteLocationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val image: String?,
    val cityName: String
) 