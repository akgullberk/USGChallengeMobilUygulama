package com.example.usgchallengemobiluygulama.core.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    object Main : Screen("main_screen")
    
    // Yeni ekranlar için:
    // object Profile : Screen("profile_screen")
    // object Settings : Screen("settings_screen")
    
    // Parametreli route'lar için:
    // object Detail : Screen("detail_screen/{id}") {
    //     fun createRoute(id: String) = "detail_screen/$id"
    // }
} 