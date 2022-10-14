package com.kartik.grevocab

import androidx.multidex.MultiDexApplication
import com.kartik.grevocab.api.apiModule
import com.kartik.grevocab.base.AndroidResProvider
import com.kartik.grevocab.base.ResProvider
import com.kartik.grevocab.repo.StocksRepo
import com.kartik.grevocab.utility.CurrencyUtils
import com.kartik.grevocab.vm.FragmentSplashViewModel
import com.kartik.grevocab.vm.FragmentStocksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@OpenForTesting
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(apiModule, myModule)
        }
    }
}

val myModule = module {

    viewModel { FragmentSplashViewModel(get(), get()) }
    viewModel { FragmentStocksViewModel(get(), get(), get()) }

    single { CurrencyUtils() }
    single { StocksRepo(get()) }
    single { AndroidResProvider(get()) as ResProvider }
}
