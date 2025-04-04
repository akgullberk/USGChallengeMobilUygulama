package com.example.usgchallengemobiluygulama.core.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Detail : Screen("detail/{locationId}") {
        fun createRoute(locationId: Int): String {
            return "detail/$locationId"
        }
    }
    
    // Yeni ekranlar için:
    // object Profile : Screen("profile_screen")
    // object Settings : Screen("settings_screen")
    
    // Parametreli route'lar için:
    // object Detail : Screen("detail_screen/{id}") {
    //     fun createRoute(id: String) = "detail_screen/$id"
    // }
} 