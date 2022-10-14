package com.kartik.grevocab.adapters.display

import com.kartik.grevocab.api.pojos.Stock
import com.kartik.grevocab.utility.CurrencyUtils
import java.math.BigDecimal
import java.util.Date

data class StockDisplay(val stock: Stock, val currencySymbol: String?, val currency: String, val time: String)

fun Stock.toDisplay(currencyUtils: CurrencyUtils) = StockDisplay(
    this,
    this.currency?.let { currencyUtils.getCurrencySymbol(it) } ?: "$",
    this.currentPriceCents?.let { (it / BigDecimal(100)).toPlainString() } ?: "N/A",
    this.currentPriceTimestamp?.let {
        getDateTimeFromEpocLongOfSeconds(it)
    } ?: "N/A"
)

fun getEmptyStocks() = StockDisplay(
    Stock("", "No stocks available", null, null, null, null),
    "",
    "",
    ""
)

private fun getDateTimeFromEpocLongOfSeconds(epoc: Long): String? {
    try {
        val netDate = Date(epoc * 1000)
        return netDate.toString()
    } catch (e: Exception) {
        return e.toString()
    }
}
