package com.example.usgchallengemobiluygulama.features.feature_splash.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.usgchallengemobiluygulama.core.domain.util.NetworkError

@Composable
fun SplashScreen(
    viewModel: SplashViewModel
) {
    val state by viewModel.state.collectAsState()
    val error by viewModel.error.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "USG Challenge",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            
            error?.let { networkError ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (networkError) {
                        is NetworkError.ConnectionError -> "İnternet bağlantısı hatası"
                        is NetworkError.ServerError -> "Sunucu hatası"
                        is NetworkError.UnknownError -> networkError.message
                    },
                    color = androidx.compose.ui.graphics.Color.Red
                )
            }
        }
    }
} 