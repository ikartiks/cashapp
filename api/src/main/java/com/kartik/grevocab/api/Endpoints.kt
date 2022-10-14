package com.kartik.grevocab.api

import com.kartik.grevocab.api.pojos.Stocks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoints {
    // https://storage.googleapis.com
    @GET("/cash-homework/cash-stocks-api/{useCase}")
    suspend fun fetchStocks(@Path(value = "useCase", encoded = true) useCase: String): Response<Stocks>
}
