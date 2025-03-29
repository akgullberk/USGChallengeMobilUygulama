package com.example.usgchallengemobiluygulama

import android.app.Application
import com.example.usgchallengemobiluygulama.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class USGApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidContext(this@USGApplication)
            modules(appModule)
        }
    }
} 