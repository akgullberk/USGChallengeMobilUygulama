package com.example.usgchallengemobiluygulama.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.usgchallengemobiluygulama.features.feature_splash.presentation.SplashScreen
import com.example.usgchallengemobiluygulama.features.feature_home.presentation.HomeScreen
import org.koin.androidx.compose.get

@Composable
fun Navigation(navController: NavHostController) {
    val navigationManager = get<NavigationManager>()

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
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(route = Screen.Home.route) {
            HomeScreen()
        }
    }
} 