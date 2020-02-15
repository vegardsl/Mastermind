package com.stjerna.mastermind.core.usecase.newgame

import com.stjerna.mastermind.core.Failure
import com.stjerna.mastermind.core.Success
import com.stjerna.mastermind.core.Try
import com.stjerna.mastermind.core.entity.MastermindGame
import com.stjerna.mastermind.core.entity.StubbedGameGateway
import org.junit.Assert.*
import org.junit.Test

internal class CreateNewGameTest {

    @Test
    fun initializeClass() {
        CreateNewGame(FakeGameGateway())
    }

    @Test
    fun createGame_returnsInitialGame() {
        when (val result = CreateNewGame(FakeGameGateway()).execute()) {
            is Success -> assertTrue(result.value.guesses.isEmpty())
            is Failure -> fail()
        }
    }

    @Test
    fun createGame_callsTheGameGateway() {
        val storage = FakeGameGateway()
        CreateNewGame(storage).execute()
        assertTrue(storage.createWasCalled)
    }
}

class FakeGameGateway : StubbedGameGateway() {

    var createWasCalled = false

    override fun create(gameID: String, code: String): Try<MastermindGame> {
        createWasCalled = true
        return Success(MastermindGame(gameID, code, false))
    }
}