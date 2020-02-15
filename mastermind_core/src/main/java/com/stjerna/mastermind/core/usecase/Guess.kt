package com.stjerna.mastermind.core.usecase

import com.stjerna.mastermind.core.Failure
import com.stjerna.mastermind.core.Success
import com.stjerna.mastermind.core.Try
import com.stjerna.mastermind.core.entity.GameGateway
import com.stjerna.mastermind.core.entity.MastermindGame
import com.stjerna.mastermind.core.usecase.score.Score

class Guess(private val storage: GameGateway) {
    fun execute(gameID: String, guess: String): Try<MastermindGame> {
        return when (val result = storage.get(gameID)) {
            is Success -> scoreGuessAndUpdate(guess, result.value)
            is Failure -> Failure(result.e)
        }
    }

    private fun scoreGuessAndUpdate(
        guess: String,
        game: MastermindGame
    ): Try<MastermindGame> {
        game.guesses.add(Pair(guess, Score(0, 0)))
        return Success(game)
    }

}