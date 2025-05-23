package com.example.usgchallengemobiluygulama.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.usgchallengemobiluygulama.features.feature_splash.presentation.SplashScreen
import com.example.usgchallengemobiluygulama.features.feature_home.presentation.HomeScreen
import com.example.usgchallengemobiluygulama.features.feature_splash.presentation.SplashViewModel
import com.example.usgchallengemobiluygulama.features.feature_favorites.presentation.FavoritesScreen
import com.example.usgchallengemobiluygulama.features.feature_detail.presentation.DetailScreen
import com.example.usgchallengemobiluygulama.features.feature_location.presentation.LocationMapScreen
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(navController: NavHostController) {
    val navigationManager = get<NavigationManager>()
    val splashViewModel: SplashViewModel = koinViewModel()
    val state by splashViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        navigationManager.navigationEvent.collect { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> {
                    navController.navigate(event.route)
                }
                is NavigationEvent.NavigateBack -> {
                    navController.popBackStack()
                }
                is NavigationEvent.NavigateToWithPopUp -> {
                    navController.navigate(event.route) {
                        popUpTo(event.popUpTo) { inclusive = event.inclusive }
                    }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(viewModel = splashViewModel)
        }
        
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
        
        composable(route = Screen.Favorites.route) {
            FavoritesScreen()
        }
        
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("locationId") { type = NavType.IntType }
            )
        ) {
            DetailScreen(it)
        }
        
        composable(
            route = Screen.LocationMap.route,
            arguments = listOf(
                navArgument("locationId") { type = NavType.IntType }
            )
        ) {
            LocationMapScreen(it)
        }
    }
} 