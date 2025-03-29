package com.example.usgchallengemobiluygulama.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigationManager {
    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    suspend fun navigate(event: NavigationEvent) {
        _navigationEvent.emit(event)
    }
} 