package com.kartik.grevocab.vm

import androidx.lifecycle.ViewModel
import com.kartik.grevocab.adapters.display.StockDisplay
import com.kartik.grevocab.adapters.display.toDisplay
import com.kartik.grevocab.api.utility.Preferences
import com.kartik.grevocab.repo.StocksRepo
import com.kartik.grevocab.utility.CurrencyUtils
import org.koin.core.component.KoinComponent

class FragmentStocksViewModel(private val preferences: Preferences, private val currencyUtils: CurrencyUtils, val stocksRepo: StocksRepo) : ViewModel(), KoinComponent {

    suspend fun getStocks(): ArrayList<StockDisplay> {
        val response = stocksRepo.getStocks(preferences.getAPIResponse().endpointUrl)
        if (response.isSuccessful) {
            val list = response.body()!!.stocks
            return ArrayList(list.map { it.toDisplay(currencyUtils) })
        }
        throw RuntimeException("error fetching stocks")
    }
}
