package com.kartik.grevocab.repo

import com.kartik.grevocab.api.ApiService

class StocksRepo(private val apiService: ApiService) {

    suspend fun getStocks(endpointURL: String) = apiService.fetchStocks(endpointURL)
}
