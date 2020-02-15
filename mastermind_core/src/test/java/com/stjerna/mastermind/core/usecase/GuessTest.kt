package com.stjerna.mastermind.core.usecase

import com.stjerna.mastermind.core.Failure
import com.stjerna.mastermind.core.Success
import com.stjerna.mastermind.core.Try
import com.stjerna.mastermind.core.entity.MastermindGame
import com.stjerna.mastermind.core.entity.StubbedGameGateway
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.jupiter.api.fail
import java.io.IOException

internal class GuessTest {
    @Test
    fun createGuess() {
        Guess(FakeGameGateway())
    }

    @Test
    fun invokeGuess() {
        Guess(FakeGameGateway()).execute(gameID = "GameID", guess = "CODE")
    }

    @Test
    fun gameNotFound() {
        when (val result = Guess(FakeGameGateway()).execute("GameID", guess = "CODE")) {
            is Failure -> assertTrue(result.e is IOException)
            is Success -> fail("Expected a failure.")
        }
    }

    @Test
    fun guessIsUpdated() {
        val testGameID = "GameID"

        val storage = FakeGameGateway(MastermindGame(testGameID, "ABCD", false))

        when (val result = Guess(storage).execute(gameID = testGameID, guess = "CODE")) {
            is Failure -> fail(result.e) // assertTrue(result.e is IOException)
            is Success -> {
                assertEquals(1, result.value.guesses.size)
            }
        }
    }
}

class FakeGameGateway(
    private val gameToReturn: MastermindGame? = null
) : StubbedGameGateway() {

    override fun put(game: MastermindGame): Try<Unit> {
        return Success(Unit)
    }

    override fun get(gameID: String): Try<MastermindGame> {
        return if (gameToReturn == null) {
            Failure(IOException("Game with ID, \"$gameID\" not found."))
        } else {
            Success(gameToReturn)
        }
    }
}