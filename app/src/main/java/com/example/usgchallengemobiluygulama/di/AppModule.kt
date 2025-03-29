package com.example.usgchallengemobiluygulama.di

import com.example.usgchallengemobiluygulama.core.data.networking.KtorClient
import org.koin.dsl.module

val appModule = module {
    single { KtorClient.create() }
} 