package com.example.usgchallengemobiluygulama.features.feature_favorites.data.local.dao

import androidx.room.*
import com.example.usgchallengemobiluygulama.features.feature_favorites.data.local.entity.FavoriteLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteLocationDao {
    @Query("SELECT * FROM favorite_locations")
    fun getAllFavorites(): Flow<List<FavoriteLocationEntity>>
    
    @Query("SELECT id FROM favorite_locations")
    suspend fun getAllFavoriteIds(): List<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteLocation: FavoriteLocationEntity)
    
    @Query("DELETE FROM favorite_locations WHERE id = :locationId")
    suspend fun deleteFavorite(locationId: Int)
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_locations WHERE id = :locationId LIMIT 1)")
    suspend fun isFavorite(locationId: Int): Boolean
} 