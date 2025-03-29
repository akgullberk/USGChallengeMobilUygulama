package com.example.usgchallengemobiluygulama.di

import com.example.usgchallengemobiluygulama.core.data.networking.KtorClient
import com.example.usgchallengemobiluygulama.core.navigation.NavigationManager
import com.example.usgchallengemobiluygulama.features.feature_splash.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { KtorClient.create() }
    single { NavigationManager() }
    viewModel { 
        SplashViewModel(
            navigationManager = get<NavigationManager>()
        )
    }
} 