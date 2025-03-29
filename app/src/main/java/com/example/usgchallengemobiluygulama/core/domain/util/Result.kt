package com.example.usgchallengemobiluygulama.core.domain.util

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: NetworkError) : Result<Nothing>()
    object Loading : Result<Nothing>()
} 