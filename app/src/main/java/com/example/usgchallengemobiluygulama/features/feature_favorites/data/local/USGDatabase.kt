package com.example.usgchallengemobiluygulama.features.feature_favorites.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.usgchallengemobiluygulama.features.feature_favorites.data.local.dao.FavoriteLocationDao
import com.example.usgchallengemobiluygulama.features.feature_favorites.data.local.entity.FavoriteLocationEntity

@Database(
    entities = [FavoriteLocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class USGDatabase : RoomDatabase() {
    abstract val favoriteLocationDao: FavoriteLocationDao
} 