package com.kartik.grevocab.vm

import androidx.lifecycle.ViewModel
import com.kartik.grevocab.adapters.display.StockDisplay
import com.kartik.grevocab.adapters.display.toDisplay
import com.kartik.grevocab.api.utility.Preferences
import com.kartik.grevocab.base.SingleLiveEvent
import com.kartik.grevocab.repo.StocksRepo
import com.kartik.grevocab.utility.CurrencyUtils
import com.kartik.grevocab.utility.LoaderState
import org.koin.core.component.KoinComponent

class FragmentStocksViewModel(private val preferences: Preferences, private val currencyUtils: CurrencyUtils, private val stocksRepo: StocksRepo) : ViewModel(), KoinComponent {

    val loaderLiveData = SingleLiveEvent<LoaderState>()

    suspend fun getStocks(): ArrayList<StockDisplay>? {
        loaderLiveData.value = LoaderState.LOADING
        val response = stocksRepo.getStocks(preferences.getAPIResponse().endpointUrl)
        if (response.isSuccessful) {
            val list = response.body()!!.stocks
            loaderLiveData.value = LoaderState.DONE
            return ArrayList(list.map { it.toDisplay(currencyUtils) })
        }
        loaderLiveData.value = LoaderState.ERROR
        return null
    }
}
