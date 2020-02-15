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
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
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

    @Test
    fun apiIntegrationTest() {
        runBlocking {
            val response1 = api.newGame()
            assertTrue(response1.isSuccessful)

            val response2 = api.guess(
                response1.body()!!,
                "AAAF")
            assertTrue(response2.isSuccessful)
        }
    }
}

interface MastermindApi {
    @GET("mastermind/newgame")
    suspend fun newGame(): Response<String>

    @POST("mastermind/guess/{gameid}")
    suspend fun guess(
        @Path("gameid") gameid: String,
        @Query("guess") guess: String
    ): Response<GuessResponse>
}

data class GuessResponse(
    val correctColors: String,
    val correctPositions: String,
    val isFinished: Boolean = false)

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