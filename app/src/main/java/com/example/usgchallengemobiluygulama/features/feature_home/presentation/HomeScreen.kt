package com.example.usgchallengemobiluygulama.features.feature_home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.City
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastIndex ->
                if (lastIndex != null && lastIndex >= state.cities.size - 2) {
                    viewModel.onAction(HomeAction.LoadNextPage)
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopBar(
                onFavoritesClick = { viewModel.onAction(HomeAction.NavigateToFavorites) }
            )
            
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.cities) { city ->
                    CityItem(
                        city = city,
                        isExpanded = state.expandedCities.contains(city.city),
                        favoritedLocations = state.favoritedLocations,
                        onCityClick = { viewModel.onAction(HomeAction.ToggleCityExpansion(city.city)) },
                        onLocationClick = { locationId -> 
                            viewModel.onAction(HomeAction.NavigateToDetail(locationId))
                        },
                        onFavoriteClick = { locationId ->
                            viewModel.onAction(HomeAction.ToggleLocationFavorite(locationId, city.city))
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { viewModel.onAction(HomeAction.CollapseAllCities) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Clear, contentDescription = "Collapse All")
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }


    }
}

@Composable
private fun TopBar(onFavoritesClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Önemli Konumlar",
            style = MaterialTheme.typography.headlineMedium
        )
        IconButton(onClick = onFavoritesClick) {
            Icon(Icons.Default.Favorite, contentDescription = "Favoriler")
        }
    }
}

@Composable
private fun CityItem(
    city: City,
    isExpanded: Boolean,
    favoritedLocations: Set<Int>,
    onCityClick: () -> Unit,
    onLocationClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = city.locations.isNotEmpty()) { onCityClick() },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (city.locations.isNotEmpty()) {
                    Icon(
                        if (isExpanded) Icons.Default.Clear else Icons.Default.Add,
                        contentDescription = if (isExpanded) "Collapse" else "Expand"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = city.city)
            }
        }

        if (isExpanded) {
            city.locations.forEach { location ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, top = 8.dp)
                        .clickable { onLocationClick(location.id) },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = location.name)
                    IconButton(onClick = { onFavoriteClick(location.id) }) {
                        Icon(
                            if (favoritedLocations.contains(location.id)) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },
                            contentDescription = "Favorite"
                        )
                    }
                }
            }
        }
    }
} 