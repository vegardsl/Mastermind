package com.stjerna.android.mastermind

import android.util.Log
import com.stjerna.android.mastermind.cloud.APIProvider
import com.stjerna.android.mastermind.cloud.GuessResponse
import com.stjerna.mastermind.core.Failure
import com.stjerna.mastermind.core.Success
import com.stjerna.mastermind.core.Try
import com.stjerna.mastermind.core.entity.MastermindGame
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

typealias GameID = String

interface MastermindUseCases {
    fun newGame(result: (Try<GameID>) -> Unit)
    fun getGame(gameID: GameID, result: (Try<MastermindGame>) -> Unit)
    fun listGameIDs(result: (Try<List<GameID>>) -> Unit)
    fun makeGuess(gameID: GameID, guess: String, result: (Try<GuessResponse>) -> Unit)
}

class MastermindRepository : MastermindUseCases {
    override fun newGame(result: (Try<GameID>) -> Unit) {
        GlobalScope.launch {
            val response = runBlocking { api.newGame() }
            if (response.isSuccessful) {
                response.body()?.let { gameID ->
                    result.invoke(Success(gameID))
                } ?: result.invoke(
                    Failure(
                        IllegalStateException("Failed to create game. Invalid game ID.")
                    )
                )
            } else {
                Log.e(TAG, "${response.errorBody()}")
                result.invoke(
                    Failure(
                        IllegalStateException("Failed to create game. ${response.errorBody()}.")
                    )
                )
            }
        }
    }

    override fun getGame(gameID: GameID, result: (Try<MastermindGame>) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listGameIDs(result: (Try<List<GameID>>) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun makeGuess(gameID: GameID, guess: String, result: (Try<GuessResponse>) -> Unit) {
        GlobalScope.launch {
            val response = runBlocking { api.guess(gameID, guess) }
            if (response.isSuccessful) {
                response.body()?.let { guessResponse ->
                    result.invoke(Success(guessResponse))
                } ?: result.invoke(
                    Failure(
                        IllegalStateException("An error occurred while guessing the code.")
                    )
                )
            } else {
                Log.e(TAG, "${response.errorBody()}")
                result.invoke(
                    Failure(
                        IllegalStateException("An error occurred while guessing the code. ${response.errorBody()}.")
                    )
                )
            }
        }
    }

    val api = APIProvider.api()

    companion object {
        val TAG: String = MastermindRepository::class.java.simpleName
    }
}