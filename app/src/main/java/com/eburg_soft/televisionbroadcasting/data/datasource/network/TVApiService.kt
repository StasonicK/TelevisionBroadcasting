package com.eburg_soft.televisionbroadcasting.data.datasource.network

import com.eburg_soft.televisionbroadcasting.core.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object TVApiService {

    fun currencyApi(): TVApi = retrofitInstance().create(TVApi::class.java)

    private fun okHttpInstance(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .build()
    }

    private fun gsonInstance(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    private fun retrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpInstance())
            .addConverterFactory(GsonConverterFactory.create(gsonInstance()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}