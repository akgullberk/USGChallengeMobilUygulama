package com.example.usgchallengemobiluygulama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.usgchallengemobiluygulama.core.navigation.Navigation
import com.example.usgchallengemobiluygulama.ui.theme.USGChallengeMobilUygulamaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            USGChallengeMobilUygulamaTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }
}