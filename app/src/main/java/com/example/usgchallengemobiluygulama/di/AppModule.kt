package com.example.usgchallengemobiluygulama.di

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.example.usgchallengemobiluygulama.core.data.networking.KtorClient
import com.example.usgchallengemobiluygulama.core.navigation.NavigationManager
import com.example.usgchallengemobiluygulama.features.feature_splash.data.repository.CityRepositoryImpl
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.repository.CityRepository
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.usecase.GetInitialCitiesUseCase
import com.example.usgchallengemobiluygulama.features.feature_splash.presentation.SplashViewModel
import com.example.usgchallengemobiluygulama.features.feature_home.presentation.HomeViewModel
import com.example.usgchallengemobiluygulama.features.feature_favorites.data.repository.FavoritesRepositoryImpl
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.repository.FavoritesRepository
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.usecase.GetFavoriteLocationsUseCase
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.usecase.ToggleFavoriteUseCase
import com.example.usgchallengemobiluygulama.features.feature_favorites.presentation.FavoritesViewModel
import com.example.usgchallengemobiluygulama.features.feature_home.domain.usecase.GetCitiesPageUseCase
import com.example.usgchallengemobiluygulama.features.feature_favorites.data.local.USGDatabase
import com.example.usgchallengemobiluygulama.features.feature_detail.data.repository.LocationDetailRepositoryImpl
import com.example.usgchallengemobiluygulama.features.feature_detail.domain.repository.LocationDetailRepository
import com.example.usgchallengemobiluygulama.features.feature_detail.domain.usecase.GetLocationDetailUseCase
import com.example.usgchallengemobiluygulama.features.feature_detail.presentation.DetailViewModel
import com.example.usgchallengemobiluygulama.features.feature_location.data.repository.LocationMapRepositoryImpl
import com.example.usgchallengemobiluygulama.features.feature_location.domain.repository.LocationMapRepository
import com.example.usgchallengemobiluygulama.features.feature_location.domain.usecase.GetLocationForMapUseCase
import com.example.usgchallengemobiluygulama.features.feature_location.presentation.LocationMapViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Core
    single { KtorClient.create() }
    single { NavigationManager() }
    
    // Database
    single {
        Room.databaseBuilder(
            androidApplication(),
            USGDatabase::class.java,
            "usg_database"
        ).build()
    }
    
    single { get<USGDatabase>().favoriteLocationDao }
    
    // Location Services
    single { LocationServices.getFusedLocationProviderClient(androidApplication()) }
    
    // Repositories
    single<CityRepository> { CityRepositoryImpl(get()) }
    single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }
    single<LocationDetailRepository> { LocationDetailRepositoryImpl(get()) }
    single<LocationMapRepository> { LocationMapRepositoryImpl(get()) }
    
    // Use Cases
    factory { GetInitialCitiesUseCase(get()) }
    factory { GetCitiesPageUseCase(get()) }
    factory { GetFavoriteLocationsUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
    factory { GetLocationDetailUseCase(get()) }
    factory { GetLocationForMapUseCase(get()) }
    
    // ViewModels
    viewModel { 
        SplashViewModel(
            navigationManager = get(),
            getInitialCitiesUseCase = get()
        )
    }
    viewModel { 
        HomeViewModel(
            navigationManager = get(),
            getCitiesPageUseCase = get(),
            toggleFavoriteUseCase = get(),
            favoritesRepository = get()
        )
    }
    viewModel {
        FavoritesViewModel(
            navigationManager = get(),
            getFavoriteLocationsUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }
    viewModel { (locationId: Int) ->
        DetailViewModel(
            navigationManager = get(),
            getLocationDetailUseCase = get(),
            toggleFavoriteUseCase = get(),
            favoritesRepository = get(),
            savedStateHandle = SavedStateHandle(mapOf("locationId" to locationId))
        )
    }
    viewModel { (locationId: Int) ->
        LocationMapViewModel(
            navigationManager = get(),
            getLocationForMapUseCase = get(),
            fusedLocationClient = get(),
            context = androidApplication(),
            savedStateHandle = SavedStateHandle(mapOf("locationId" to locationId))
        )
    }
}