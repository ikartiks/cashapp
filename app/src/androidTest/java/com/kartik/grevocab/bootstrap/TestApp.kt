package com.kartik.grevocab.bootstrap

import android.app.Application
import com.kartik.grevocab.api.apiModule
import com.kartik.grevocab.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

/**
 * Used by KoinTest runner to create mock Koin dependencies
 *
 */
class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(org.koin.core.logger.Level.ERROR) // creates a crash otherwise although this was solved earlier
            androidContext(this@TestApp)
            modules(myModule, apiModule)
        }
    }

    fun injectModule(module: Module) {
        unloadKoinModules(module)
        loadKoinModules(module)
    }
    fun injectModules(modules: List<Module>) {
        unloadKoinModules(modules)
        loadKoinModules(modules)
    }
}
