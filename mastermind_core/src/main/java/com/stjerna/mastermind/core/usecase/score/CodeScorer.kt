package com.stjerna.mastermind.core.usecase.score

class CodeScorer(code: String, guess: String) {

    val isPerfectScore: Boolean
    val score: Score

    val positionsUsed = booleanArrayOf(false, false, false, false)
    val guessUsed = booleanArrayOf(false, false, false, false)

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

        for (codePos in guessList.indices) {
            val guessSymbol = guessList[codePos]
            if (codeList[codePos] == guessSymbol) {
                positionsUsed[codePos] = true
                guessUsed[codePos] = true
                correctPositions++
            }
            for (guessPos in codeList.indices) {
                if (codePos != guessPos && codeList[codePos] == guessList[guessPos] && !positionsUsed[codePos] && !guessUsed[guessPos]) {
                    positionsUsed[codePos] = true
                    guessUsed[guessPos] = true
                    correctSymbols++
                }
            }
        }

        score = Score(correctPositions, correctSymbols)
        isPerfectScore = correctPositions == GameRuleSet.codeSize
    }


}
