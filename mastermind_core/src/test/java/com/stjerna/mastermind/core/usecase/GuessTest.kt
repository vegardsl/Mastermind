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
    fun guessListIsUpdated() {
        val testGameID = "GameID"

        val storage = FakeGameGateway(MastermindGame(testGameID, "ABCD", false))

        when (val result = Guess(storage).execute(gameID = testGameID, guess = "CODE")) {
            is Failure -> fail(result.e)
            is Success -> {
                assertEquals(1, result.value.guesses.size)
            }
        }
    }

    @Test
    fun updatedGameIsStored() {
        val testGameID = "GameID"
        val storage = FakeGameGateway(MastermindGame(testGameID, "ABCD", false))
        val result = Guess(storage).execute(gameID = testGameID, guess = "CODE")
        assertTrue(result is Success)
        assertTrue(storage.putWasCalled)
    }

    @Test
    fun errorsWhenStoringAreCaught() {
        val testGameID = "GameID"
        val storage = FakeGameGateway(MastermindGame(testGameID, "ABCD", false))
        storage.putShallFail = true
        val result = Guess(storage).execute(gameID = testGameID, guess = "CODE")
        assertTrue(result is Failure)
        assertTrue(storage.putWasCalled)
    }
}

class FakeGameGateway(
    private val gameToReturn: MastermindGame? = null
) : StubbedGameGateway() {

    var putShallFail = false
    var putWasCalled = false

    override fun put(game: MastermindGame): Try<Unit> {
        putWasCalled = true
        return if (putShallFail) Failure(IOException(""))
        else Success(Unit)
    }

    override fun get(gameID: String): Try<MastermindGame> {
        return if (gameToReturn == null) {
            Failure(IOException("Game with ID, \"$gameID\" not found."))
        } else {
            Success(gameToReturn)
        }
    }
}