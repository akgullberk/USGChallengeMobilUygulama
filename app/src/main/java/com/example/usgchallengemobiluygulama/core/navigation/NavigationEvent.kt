package com.example.usgchallengemobiluygulama.core.navigation

sealed class NavigationEvent {
    data class NavigateTo(val route: String) : NavigationEvent()
    object NavigateBack : NavigationEvent()
    data class NavigateToWithPopUp(
        val route: String,
        val popUpTo: String,
        val inclusive: Boolean = false
    ) : NavigationEvent()
} 