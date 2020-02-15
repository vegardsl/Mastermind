package com.stjerna.mastermind.core.usecase.score

class CodeScorer(code: String, guess: String) {

    val score: Score

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

        var correctSymbols = 0
        var correctPositions = 0

        for (i in guessList.indices) {
            val guessSymbol = guessList[i]
            if (codeList.contains(guessSymbol)) correctSymbols++
            if (codeList[i] == guessSymbol) correctPositions++
        }

        score = Score(correctPositions, correctSymbols - correctPositions)
    }


}
