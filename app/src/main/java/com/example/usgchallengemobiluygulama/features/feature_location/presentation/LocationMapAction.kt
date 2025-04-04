package com.example.usgchallengemobiluygulama.features.feature_location.presentation

sealed interface LocationMapAction {
    object NavigateBack : LocationMapAction
    object GetDirections : LocationMapAction
    object FocusUserLocation : LocationMapAction
    object RequestLocationPermission : LocationMapAction
    object OpenLocationSettings : LocationMapAction
    object OpenAppSettings : LocationMapAction
    object DismissLocationPermissionDialog : LocationMapAction
} 