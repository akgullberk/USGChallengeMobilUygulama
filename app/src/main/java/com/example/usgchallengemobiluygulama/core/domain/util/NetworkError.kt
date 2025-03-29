package com.example.usgchallengemobiluygulama.core.domain.util

sealed class NetworkError {
    object ConnectionError : NetworkError()
    object ServerError : NetworkError()
    data class UnknownError(val message: String) : NetworkError()
} 