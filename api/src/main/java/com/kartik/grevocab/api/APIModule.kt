package com.kartik.grevocab.api

import com.kartik.grevocab.api.utility.Preferences
import org.koin.dsl.module

/**
 * Provides repo module for Koin to be fetched from app
 */
val apiModule = module {
    single { Preferences(get()) }
    single { RetrofitClient.provideEndpoints() }
    single { ApiService(get()) }
}
