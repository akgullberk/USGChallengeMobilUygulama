package com.example.usgchallengemobiluygulama.features.feature_location.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavBackStackEntry
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LocationMapScreen(
    navBackStackEntry: NavBackStackEntry
) {
    val locationId = navBackStackEntry.arguments?.getInt("locationId") ?: -1
    val viewModel: LocationMapViewModel = koinViewModel { parametersOf(locationId) }
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    
    // Konum izni için launcher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewModel.updateLocationPermissionStatus(isGranted)
    }
    
    // GPS durumunu kontrol etmek için
    LaunchedEffect(Unit) {
        checkGpsAndRequestPermission(context, viewModel, requestPermissionLauncher)
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Google Maps
        state.mapLocation?.let { location ->
            val locationLatLng = LatLng(location.coordinates.lat, location.coordinates.lng)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(locationLatLng, 15f)
            }
            
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = state.isLocationPermissionGranted && state.isGpsEnabled
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = true,
                    myLocationButtonEnabled = false
                )
            ) {
                // Konum marker'ı
                Marker(
                    state = MarkerState(position = locationLatLng),
                    title = location.name,
                    snippet = location.cityName,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                )
                
                // Kullanıcı konumu varsa ve izin verildiyse
                state.userLocation?.let { userCoords ->
                    if (state.isLocationPermissionGranted && state.isGpsEnabled) {
                        val userLatLng = LatLng(userCoords.lat, userCoords.lng)
                        // Kullanıcı konumu marker'ı eklenebilir, ancak isMyLocationEnabled true olduğu için
                        // Google Maps zaten mavi nokta gösterecektir
                    }
                }
            }
            
            // Top Bar
            TopAppBar(
                title = location.name,
                onBackClick = { viewModel.onAction(LocationMapAction.NavigateBack) },
                modifier = Modifier.align(Alignment.TopCenter)
            )
            
            // Bottom Buttons
            BottomButtons(
                onGetDirectionsClick = {
                    openMapsWithDirections(context, location.coordinates.lat, location.coordinates.lng, location.name)
                },
                onMyLocationClick = {
                    if (!state.isLocationPermissionGranted) {
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    } else if (!state.isGpsEnabled) {
                        openLocationSettings(context)
                    } else {
                        viewModel.getUserLocation()
                        state.userLocation?.let { userCoords ->
                            val userLatLng = LatLng(userCoords.lat, userCoords.lng)
                            cameraPositionState.move(CameraUpdateFactory.newLatLng(userLatLng))
                        }
                    }
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
        
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        
        state.error?.let { error ->
            ErrorMessage(
                error = error,
                onBackClick = { viewModel.onAction(LocationMapAction.NavigateBack) },
                modifier = Modifier.align(Alignment.Center)
            )
        }
        
        // Konum izni dialog'u
        if (state.showLocationPermissionDialog) {
            LocationPermissionDialog(
                onConfirm = {
                    viewModel.onAction(LocationMapAction.RequestLocationPermission)
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
                onDismiss = {
                    viewModel.onAction(LocationMapAction.DismissLocationPermissionDialog)
                }
            )
        }
    }
}

@Composable
fun TopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        shadowElevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
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
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            
            // Sağ tarafta boşluk bırakmak için
            Box(modifier = Modifier.size(48.dp))
        }
    }
}

@Composable
fun BottomButtons(
    onGetDirectionsClick: () -> Unit,
    onMyLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        shadowElevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onGetDirectionsClick,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Yol Tarifi Al")
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            FloatingActionButton(
                onClick = onMyLocationClick,
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Konumum"
                )
            }
        }
    }
}

@Composable
fun ErrorMessage(
    error: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Hata: $error",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onBackClick
        ) {
            Text("Geri Dön")
        }
    }
}

@Composable
fun LocationPermissionDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Konum İzni",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Kendi konumunu haritada görmek ister misin?",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Hayır")
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Evet")
                    }
                }
            }
        }
    }
}

// Yardımcı fonksiyonlar
private fun checkGpsAndRequestPermission(
    context: Context,
    viewModel: LocationMapViewModel,
    requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    viewModel.updateGpsStatus(isGpsEnabled)
}

private fun openLocationSettings(context: Context) {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    context.startActivity(intent)
}

private fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

private fun openMapsWithDirections(context: Context, lat: Double, lng: Double, locationName: String) {
    val encodedName = Uri.encode(locationName)
    val uri = Uri.parse("google.navigation:q=$lat,$lng&mode=d")
    val mapIntent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
    }
    
    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    } else {
        // Google Maps yüklü değilse, web tarayıcıda aç
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$lat,$lng&destination_place_id=$encodedName")
        )
        context.startActivity(webIntent)
    }
} 