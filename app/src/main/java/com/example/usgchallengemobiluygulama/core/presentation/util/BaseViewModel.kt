package com.example.usgchallengemobiluygulama.core.presentation.util

import androidx.lifecycle.ViewModel
import com.example.usgchallengemobiluygulama.core.domain.util.NetworkError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {
    protected val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    protected val _error = MutableStateFlow<NetworkError?>(null)
    val error = _error.asStateFlow()

    protected fun handleError(error: NetworkError) {
        _error.value = error
    }
} 