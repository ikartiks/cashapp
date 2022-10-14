package com.kartik.grevocab.api

import androidx.test.espresso.IdlingRegistry
import com.google.gson.GsonBuilder
import com.jakewharton.espresso.OkHttp3IdlingResource
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Note if you want something like telstra, where responses are fetched from a file which backend gives, then you probably have to checkout them
 * inside debug/assets where all our json files are stored
 *
 */
class ApiServiceTest : KoinTest {

    private val mockWebServer = MockWebServer()

    /**
     * Runs before every test is executed
     *
     */
    @BeforeEach
    fun setup() {
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                RetrofitClient.getClient()
            )
        )
    }

    /**
     * Called after all tests have finished executing
     *
     */
    @AfterEach
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSuccessfulResponse() {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(FileReader.readStringFromFile("groups.json"))
        )

        runBlocking {
            val apiService = ApiService(getEndpointsForTests())
            val response = apiService.fetchStocks()
            val groups = response.body()
            val group = groups?.get(0)
            assertEquals(group?.name, "High Frequency - I")
        }
    }

    /**
     * Tests if failure callback is called. so just asserting 1:1
     *
     */
    @Test
    fun testFailureResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(FileReader.readStringFromFile("fail.json"))
        )
        val apiService = ApiService(getEndpointsForTests())
        runBlocking {
            val response = apiService.fetchStocks()
            assertEquals(response.code(), 404)
        }
    }

    private fun getEndpointsForTests(): Endpoints {
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val builder = GsonBuilder()
        val gson = builder.create()
        val factory = GsonConverterFactory.create(gson)
        return Retrofit.Builder()
            .addConverterFactory(factory)
            .baseUrl("http://127.0.0.1:8080/")
            .client(okHttpClient)
            .build().create(Endpoints::class.java)
    }
}
