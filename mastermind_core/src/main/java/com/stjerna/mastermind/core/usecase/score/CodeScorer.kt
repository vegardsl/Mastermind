package com.stjerna.mastermind.core.usecase.score

class CodeScorer(code: String, guess: String) {

    init {
        require(code.length == GameRuleSet.codeSize)
        require(guess.length == GameRuleSet.codeSize)

        val codeList = code.chunked(1)
        codeList.forEach { codeSymbol ->
            require(GameRuleSet.symbolList.contains(codeSymbol))
        }

        val guessList = guess.chunked(1)
        guessList.forEach { guessSymbol ->
            require(GameRuleSet.symbolList.contains(guessSymbol))
        }
    }

    val score: Score =
        Score(0, 0)
}
