package com.kartik.grevocab.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun provideEndpoints(): Endpoints {
        return provideRetrofitInstance()
            .create(Endpoints::class.java)
    }

    private fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(getGSONConverterFactory())
            .client(getClient())
            .build()
    }

    private fun getBaseUrl(): String {
        return "https://storage.googleapis.com/"
    }

    private fun getGSONConverterFactory(): GsonConverterFactory {
        val builder = GsonBuilder()
        val gson = builder.create()
        return GsonConverterFactory.create(gson)
    }

    fun getClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
}
