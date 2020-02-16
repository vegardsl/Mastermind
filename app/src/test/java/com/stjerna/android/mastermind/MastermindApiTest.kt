package com.stjerna.android.mastermind

import com.stjerna.android.mastermind.cloud.APIProvider
import com.stjerna.android.mastermind.cloud.MastermindApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MastermindApiTest {

    private lateinit var api: MastermindApi

    @Before
    fun setUp() {
        api = APIProvider.api()
    }

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
                "AAAF"
            )
            assertTrue(response2.isSuccessful)
        }
    }
}
