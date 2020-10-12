package com.eburg_soft.televisionbroadcasting.data.datasource.network

import com.eburg_soft.televisionbroadcasting.core.Constants.BASE_URL
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

object TVApiService {

    val MAX_READ_TIMEOUT: Long = 10
    val MAX_CONNECT_TIMEOUT: Long = 10

    fun currencyApi(): TVApi = retrofitInstance().create(TVApi::class.java)

    private fun okHttpInstance(): OkHttpClient = OkHttpClient().newBuilder()
        //  Stetho interceptor
        .addNetworkInterceptor(StethoInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(MAX_CONNECT_TIMEOUT, SECONDS)
        .readTimeout(MAX_READ_TIMEOUT, SECONDS)
        .build()

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