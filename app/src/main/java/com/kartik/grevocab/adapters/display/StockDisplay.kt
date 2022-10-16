package com.kartik.grevocab.adapters.display

import com.kartik.grevocab.api.pojos.Stock
import com.kartik.grevocab.utility.CurrencyUtils
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date

data class StockDisplay(val stock: Stock, val amount: String?, val currency: String, val time: String, val available: String)

fun Stock.toDisplay(currencyUtils: CurrencyUtils) = StockDisplay(
    this,
    (
        (this.currency?.let { currencyUtils.getCurrencySymbol(it) } ?: "$") +
            this.currentPriceCents?.let { (it / BigDecimal(100)).toPlainString() }
        ),
    this.currentPriceCents?.let { (it / BigDecimal(100)).toPlainString() } ?: "N/A",
    this.currentPriceTimestamp?.let {
        getDateTimeFromEpocLongOfSeconds(it)
    } ?: "N/A",
    this.quantity?.let { "$it available" } ?: ""
)

fun getEmptyStocks() = StockDisplay(
    Stock("", "No stocks available", null, null, null, null),
    "",
    "",
    "",
    ""
)

private fun getDateTimeFromEpocLongOfSeconds(epoc: Long): String? {
    val date = Date(epoc * 1000)
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
    return formatter.format(date)
}
