package com.example.usgchallengemobiluygulama.features.feature_location.presentation

import com.example.usgchallengemobiluygulama.features.feature_location.domain.model.MapLocation
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.Coordinates

data class LocationMapState(
    val mapLocation: MapLocation? = null,
    val userLocation: Coordinates? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showLocationPermissionDialog: Boolean = false,
    val isLocationPermissionGranted: Boolean = false,
    val isGpsEnabled: Boolean = false
) 