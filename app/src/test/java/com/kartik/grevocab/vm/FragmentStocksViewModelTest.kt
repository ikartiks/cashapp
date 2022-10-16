package com.kartik.grevocab.vm

import com.google.gson.reflect.TypeToken
import com.kartik.grevocab.BaseTest
import com.kartik.grevocab.FileReader
import com.kartik.grevocab.MockResProvider
import com.kartik.grevocab.api.pojos.Stocks
import com.kartik.grevocab.api.utility.APIResponse
import com.kartik.grevocab.api.utility.Preferences
import com.kartik.grevocab.repo.StocksRepo
import com.kartik.grevocab.utility.CurrencyUtils
import com.kartik.grevocab.utility.LoaderState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FragmentStocksViewModelTest : BaseTest() {

    private val mockResProvider: MockResProvider = mockk() // ask me about this during a run through
    private val preferences: Preferences = mockk()
    private val currencyUtils: CurrencyUtils = mockk()
    private val stocksRepo: StocksRepo = mockk()
    private lateinit var vm: FragmentStocksViewModel

    @BeforeEach
    fun setUp() {
        vm = FragmentStocksViewModel(preferences, currencyUtils, stocksRepo)
    }

    @Test
    fun testViewModelForSuccessfulResponse() {
        coEvery { stocksRepo.getStocks(any()) } returns parseSuccessResponse()
        every { preferences.getAPIResponse() } returns APIResponse.SUCCESS
        every { currencyUtils.getCurrencySymbol(any()) } returns "$"

        runBlocking {
            val stocks = vm.getStocks()
            assertEquals(vm.loaderLiveData.value, LoaderState.DONE)
            assertEquals(stocks?.size, 17)
            val sd = stocks?.get(0)
            assertEquals(sd?.stock?.name, "Twitter, Inc.")
            assertEquals(sd?.amount, "$38")
            assertEquals(sd?.time, "12/11/2021 05:08")
        }
    }

    @Test
    fun testViewModelForSuccessfulEmptyResponse() {
        coEvery { stocksRepo.getStocks(any()) } returns parseSuccessEmptyResponse()
        every { preferences.getAPIResponse() } returns APIResponse.SUCCESS
        every { currencyUtils.getCurrencySymbol(any()) } returns "$"

        runBlocking {
            val stocks = vm.getStocks()
            assertEquals(vm.loaderLiveData.value, LoaderState.DONE)
            assertEquals(stocks?.size, 0)
        }
    }

    @Test
    fun testViewModelForFailureResponse() {
        coEvery { stocksRepo.getStocks(any()) } returns parseFailureResponse()
        every { preferences.getAPIResponse() } returns APIResponse.SUCCESS
        every { currencyUtils.getCurrencySymbol(any()) } returns "$"

        runBlocking {
            val stocks = vm.getStocks()
            assertEquals(vm.loaderLiveData.value, LoaderState.ERROR)
            assertEquals(stocks, null)
        }
    }

    private fun parseSuccessResponse(): Response<Stocks> {
        return Response.success(FileReader.getData("success.json", object : TypeToken<Stocks>() {}.type))
    }
    private fun parseSuccessEmptyResponse(): Response<Stocks> {
        return Response.success(FileReader.getData("success_empty.json", object : TypeToken<Stocks>() {}.type))
    }
    private fun parseFailureResponse(): Response<Stocks> {
        return Response.error(400, ResponseBody.create("application/json".toMediaTypeOrNull(), "{}"))
    }
}
