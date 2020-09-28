package com.eburg_soft.televisionbroadcasting.data.datasource.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

// TODO: 26.09.2020 write BASE_URL
const val BASE_URL = ""

object TVApiService {

    fun currencyApi(): TVApi = retrofitInstance().create(TVApi::class.java)

    private fun okHttpInstance(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.NONE else HttpLoggingInterceptor.Level.BASIC


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