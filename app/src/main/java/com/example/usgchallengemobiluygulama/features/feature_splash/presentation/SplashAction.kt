package com.example.usgchallengemobiluygulama.features.feature_splash.presentation

sealed interface SplashAction {
    object OnSplashFinished : SplashAction
    object NavigateToMain : SplashAction
} 