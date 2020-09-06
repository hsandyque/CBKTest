package com.test.cbktest.connection

import com.test.cbktest.csvconvert.CsvConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private fun getOkHttpClient(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logger)

        return builder
    }

    private val BASE_URL = "https://data.taipei/"

    val jsonReqApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return@lazy retrofit.create(ApiInterface::class.java)
    }
    val csvReqApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(CsvConverterFactory.create())
            .build()
        return@lazy retrofit.create(ApiInterface::class.java)
    }

}