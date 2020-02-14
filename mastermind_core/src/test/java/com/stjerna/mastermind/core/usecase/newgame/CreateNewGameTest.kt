package com.stjerna.mastermind.core.usecase.newgame

import com.stjerna.mastermind.core.Failure
import com.stjerna.mastermind.core.Success
import com.stjerna.mastermind.core.Try
import com.stjerna.mastermind.core.entity.MastermindGame
import com.stjerna.mastermind.core.entity.StubbedGameGateway
import org.junit.Assert.*
import org.junit.Test

internal class CreateNewGameTest : NewGamePresenter {

    var newGameResult: Try<MastermindGame>? = null

    override fun present(result: Try<MastermindGame>) {
        newGameResult = result
    }

    @Test
    fun initializeClass() {
        CreateNewGame(this, FakeGameGateway())
    }

    @Test
    fun createNewGame_resultIsCalled() {
        CreateNewGame(this, FakeGameGateway()).execute()
        assertNotNull(newGameResult)
    }

    @Test
    fun createGame_returnsInitialGame() {
        CreateNewGame(this, FakeGameGateway()).execute()
        when (val result = newGameResult) {
            is Success -> assertTrue(result.value.guesses.isEmpty())
            is Failure -> fail()
        }
    }

    @Test
    fun createGame_callsTheGameGateway() {
        val storage = FakeGameGateway()
        CreateNewGame(this, storage).execute()
        assertTrue(storage.createWasCalled)
    }
}

class FakeGameGateway : StubbedGameGateway() {

    var createWasCalled = false

    override fun create(gameID: String, result: (Try<MastermindGame>) -> Unit) {
        createWasCalled = true
        result.invoke(Success(MastermindGame(("123"))))
    }
}