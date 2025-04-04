package com.example.usgchallengemobiluygulama.features.feature_location.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.usgchallengemobiluygulama.core.domain.util.Result
import com.example.usgchallengemobiluygulama.core.navigation.NavigationEvent
import com.example.usgchallengemobiluygulama.core.navigation.NavigationManager
import com.example.usgchallengemobiluygulama.core.presentation.util.BaseViewModel
import com.example.usgchallengemobiluygulama.features.feature_location.domain.usecase.GetLocationForMapUseCase
import com.example.usgchallengemobiluygulama.features.feature_splash.domain.model.Coordinates
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationMapViewModel(
    private val navigationManager: NavigationManager,
    private val getLocationForMapUseCase: GetLocationForMapUseCase,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val context: Context,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    
    private val _state = MutableStateFlow(LocationMapState())
    val state = _state.asStateFlow()
    
    private val locationId: Int = checkNotNull(savedStateHandle["locationId"])
    
    init {
        loadLocationForMap()
        checkLocationPermissionAndGps()
    }
    
    fun onAction(action: LocationMapAction) {
        when (action) {
            LocationMapAction.NavigateBack -> navigateBack()
            LocationMapAction.GetDirections -> getDirections()
            LocationMapAction.FocusUserLocation -> getUserLocation()
            LocationMapAction.RequestLocationPermission -> {
                _state.value = state.value.copy(showLocationPermissionDialog = false)
                // Burada izin isteme işlemi yapılacak, UI tarafında gerçekleşecek
            }
            LocationMapAction.OpenLocationSettings -> {
                // GPS ayarlarını açma işlemi UI tarafında gerçekleşecek
            }
            LocationMapAction.OpenAppSettings -> {
                // Uygulama ayarlarını açma işlemi UI tarafında gerçekleşecek
            }
            LocationMapAction.DismissLocationPermissionDialog -> {
                _state.value = state.value.copy(showLocationPermissionDialog = false)
            }
        }
    }
    
    private fun loadLocationForMap() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true, error = null)
            
            when (val result = getLocationForMapUseCase(locationId)) {
                is Result.Success -> {
                    _state.value = state.value.copy(
                        mapLocation = result.data,
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    _state.value = state.value.copy(

                        isLoading = false
                    )
                }
                is Result.Loading -> {
                    _state.value = state.value.copy(isLoading = true)
                }
            }
        }
    }
    
    private fun navigateBack() {
        viewModelScope.launch {
            navigationManager.navigate(NavigationEvent.NavigateBack)
        }
    }
    
    private fun getDirections() {
        // Yol tarifi alma işlemi UI tarafında gerçekleşecek
    }
    
    @SuppressLint("MissingPermission")
    fun getUserLocation(isPermissionGranted: Boolean = false) {
        viewModelScope.launch {
            if (isPermissionGranted || state.value.isLocationPermissionGranted) {
                _state.value = state.value.copy(isLocationPermissionGranted = true)
                
                if (isGpsEnabled()) {
                    _state.value = state.value.copy(isGpsEnabled = true)
                    
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        location?.let {
                            val userCoordinates = Coordinates(it.latitude, it.longitude)
                            _state.value = state.value.copy(userLocation = userCoordinates)
                        }
                    }
                } else {
                    _state.value = state.value.copy(isGpsEnabled = false)
                }
            } else {
                _state.value = state.value.copy(
                    showLocationPermissionDialog = true,
                    isLocationPermissionGranted = false
                )
            }
        }
    }
    
    fun updateLocationPermissionStatus(isGranted: Boolean) {
        _state.value = state.value.copy(isLocationPermissionGranted = isGranted)
        if (isGranted) {
            getUserLocation(true)
        }
    }
    
    fun updateGpsStatus(isEnabled: Boolean) {
        _state.value = state.value.copy(isGpsEnabled = isEnabled)
        if (isEnabled && state.value.isLocationPermissionGranted) {
            getUserLocation(true)
        }
    }
    
    private fun isGpsEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    
    private fun checkLocationPermissionAndGps() {
        // Bu fonksiyon UI tarafında izin kontrolü yapıldıktan sonra çağrılacak
        // Şimdilik sadece GPS kontrolü yapıyoruz
        _state.value = state.value.copy(isGpsEnabled = isGpsEnabled())
        
        // İlk açılışta konum izni kontrolü yapılacak ve dialog gösterilecek
        if (!state.value.isLocationPermissionGranted && !state.value.showLocationPermissionDialog) {
            _state.value = state.value.copy(showLocationPermissionDialog = true)
        }
    }
} 