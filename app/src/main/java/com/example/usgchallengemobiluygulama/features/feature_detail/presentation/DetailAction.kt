package com.example.usgchallengemobiluygulama.features.feature_detail.presentation

sealed interface DetailAction {
    object ToggleFavorite : DetailAction
    object NavigateBack : DetailAction
    object ShowOnMap : DetailAction
} 