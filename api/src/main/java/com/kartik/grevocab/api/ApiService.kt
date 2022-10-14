package com.kartik.grevocab.api

class ApiService(var endpoints: Endpoints) {

    suspend fun fetchStocks(useCase: String) = endpoints.fetchStocks(useCase)
}
