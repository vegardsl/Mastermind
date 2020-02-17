package com.stjerna.android.mastermind.cloud

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.stjerna.android.mastermind.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object APIProvider {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okhttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            MyInterceptor(BuildConfig.apiBaseUrl, BuildConfig.apiKey)
        ).callTimeout(20, TimeUnit.SECONDS).build()

    fun api(): MastermindApi = Retrofit.Builder()
        .baseUrl(BuildConfig.apiBaseUrl)
        .client(okhttp)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build().create(MastermindApi::class.java)
}
