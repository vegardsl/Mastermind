package com.stjerna.mastermind.core.usecase

import com.stjerna.mastermind.core.Failure
import com.stjerna.mastermind.core.MyPair
import com.stjerna.mastermind.core.Success
import com.stjerna.mastermind.core.Try
import com.stjerna.mastermind.core.entity.GameGateway
import com.stjerna.mastermind.core.entity.MastermindGame
import com.stjerna.mastermind.core.usecase.score.CodeScorer
import com.stjerna.mastermind.core.usecase.score.Score

class GuessInteractor(private val storage: GameGateway) {
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
        val codeScorer = CodeScorer(game.code, guess)
        game.guesses.add(MyPair(guess, codeScorer.score))
        game.isFinished = codeScorer.isPerfectScore
        return when(val result = storage.put(game)) {
            is Success -> Success(game)
            is Failure -> Failure(result.e)
        }
    }

}