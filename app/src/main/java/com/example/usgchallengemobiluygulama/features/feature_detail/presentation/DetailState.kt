package com.example.usgchallengemobiluygulama.features.feature_detail.presentation

import com.example.usgchallengemobiluygulama.features.feature_detail.domain.model.LocationDetail

data class DetailState(
    val locationDetail: LocationDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFavorite: Boolean = false
) 