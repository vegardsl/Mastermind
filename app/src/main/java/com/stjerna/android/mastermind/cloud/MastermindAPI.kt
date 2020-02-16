package com.stjerna.android.mastermind.cloud

import okhttp3.Interceptor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
    val guess: String,
    val attemptNumber: Int,
    val correctColors: String,
    val correctPositions: String,
    val finished: Boolean)

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