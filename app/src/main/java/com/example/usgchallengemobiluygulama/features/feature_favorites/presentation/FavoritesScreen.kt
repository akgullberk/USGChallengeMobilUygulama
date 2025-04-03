package com.example.usgchallengemobiluygulama.features.feature_favorites.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.usgchallengemobiluygulama.features.feature_favorites.domain.model.FavoriteLocation
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            onBackClick = { viewModel.onAction(FavoritesAction.NavigateBack) }
        )
        
        if (state.favoriteLocations.isEmpty()) {
            EmptyFavorites(modifier = Modifier.weight(1f))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.favoriteLocations) { favorite ->
                    FavoriteItem(
                        favorite = favorite,
                        onItemClick = { viewModel.onAction(FavoritesAction.NavigateToDetail(favorite.id)) },
                        onFavoriteClick = { viewModel.onAction(FavoritesAction.ToggleFavorite(favorite.id, favorite.city)) }
                    )
                }
            }
        }
    }
    
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
        }
        Text(
            text = "Favorilerim",
            style = MaterialTheme.typography.headlineMedium
        )
        // Sağ tarafta boşluk bırakmak için
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
private fun EmptyFavorites(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Henüz favori konum eklemediniz",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun FavoriteItem(
    favorite: FavoriteLocation,
    onItemClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onItemClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = favorite.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = favorite.city,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = favorite.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favorilerden Çıkar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
} 