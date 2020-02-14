package com.stjerna.android.mastermind

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

class MastermindApiTest {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okhttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            MyInterceptor(BuildConfig.apiBaseUrl, BuildConfig.apiKey)
        ).callTimeout(10, TimeUnit.SECONDS).build()

    private val api: MastermindApi = Retrofit.Builder()
        .baseUrl(BuildConfig.apiBaseUrl)
        .client(okhttp)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build().create(MastermindApi::class.java)

    @Test
    fun test() {
        runBlocking {
            val response = api.newGame()
            assertTrue(response.isSuccessful)
        }
    }
}

interface MastermindApi {
    @GET("dev/mastermind/new-game")
    suspend fun newGame(): Response<ResponseBody>
}

class MyInterceptor(
    private val hostUrl: String,
    private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()

        // newRequestBuilder.addHeader("x-api-key", apiKey)
        return chain.proceed(newRequestBuilder.build())
    }

}