package com.kartik.grevocab.utility

import java.util.Currency
import java.util.Locale
import java.util.SortedMap
import java.util.TreeMap

class CurrencyUtils {

    private var currencyLocaleMap: SortedMap<Currency, Locale> = TreeMap { p0, p1 ->
        p0?.currencyCode?.compareTo(
            p1?.currencyCode ?: ""
        ) ?: 0
    }

    init {
        for (locale in Locale.getAvailableLocales()) {
            try {
                val currency = Currency.getInstance(locale)
                currencyLocaleMap[currency] = locale
            } catch (e: Exception) {
            }
        }
    }

    fun getCurrencySymbol(currencyCode: String): String? {
        val currency: Currency = Currency.getInstance(currencyCode)
        println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap[currency]))
        return currency.getSymbol(currencyLocaleMap[currency])
    }
}
