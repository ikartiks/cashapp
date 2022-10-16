package com.kartik.grevocab

import com.google.gson.reflect.TypeToken
import com.kartik.grevocab.api.ApiService
import com.kartik.grevocab.api.pojos.Stocks
import com.kartik.grevocab.repo.StocksRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.math.BigDecimal
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class StocksRepoTest : BaseTest() {
    private val apiService: ApiService = mockk()
    private lateinit var stocksRepo: StocksRepo

    @BeforeEach
    fun setUp() {
        stocksRepo = StocksRepo(apiService)
    }

    @Test
    fun testViewModelForSuccessfulResponse() {
        coEvery { apiService.fetchStocks(any()) } returns parseSuccessResponse()

        runBlocking {
            val stocks = stocksRepo.getStocks("portfolio.json")
            assertEquals(stocks.isSuccessful, true)
            val list = stocks.body()?.stocks
            assertEquals(list?.size, 17)
            val sd = list?.get(0)
            assertEquals(sd?.name, "Twitter, Inc.")
            assertEquals(sd?.currency, "USD")
            assertEquals(sd?.currentPriceCents, BigDecimal(3833))
        }
    }

    private fun parseSuccessResponse(): Response<Stocks> {
        return Response.success(FileReader.getData("success.json", object : TypeToken<Stocks>() {}.type))
    }
}
