package com.stjerna.mastermind.core.usecase.newgame

import com.stjerna.mastermind.core.Failure
import com.stjerna.mastermind.core.Success
import com.stjerna.mastermind.core.Try
import com.stjerna.mastermind.core.entity.GameGateway
import com.stjerna.mastermind.core.entity.MastermindGame
import java.util.*

class CreateNewGame(private val storage: GameGateway) {
    fun execute(): Try<MastermindGame> {
        return when (val result = storage.create(UUID.randomUUID().toString(), "ABCD")) {
            is Success -> Success(result.value)
            is Failure -> Failure(result.e)
        }
    }

}