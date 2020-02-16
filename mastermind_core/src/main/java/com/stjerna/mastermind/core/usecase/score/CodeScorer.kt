package com.stjerna.mastermind.core.usecase.score

class CodeScorer(code: String, guess: String) {

    val isPerfectScore: Boolean
    val score: Score

    private val positionsUsed = booleanArrayOf(false, false, false, false)
    private val guessUsed = booleanArrayOf(false, false, false, false)

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

        val correctSymbols = countCorrectSymbols(guessList, codeList)
        val correctPositions = countCorrectPositions(guessList, codeList)

        score = Score(correctPositions, correctSymbols)
        isPerfectScore = correctPositions == GameRuleSet.codeSize
    }

    private fun countCorrectPositions(
        guessList: List<String>,
        codeList: List<String>
    ): Int {
        var correctPositions = 0
        for (codePos in guessList.indices) {
            val guessSymbol = guessList[codePos]
            if (codeList[codePos] == guessSymbol) {
                positionsUsed[codePos] = true
                guessUsed[codePos] = true
                correctPositions++
            }
        }
        return correctPositions
    }

    private fun countCorrectSymbols(
        guessList: List<String>,
        codeList: List<String>
    ): Int {
        var correctSymbols = 0
        for (codePos in guessList.indices) {
            for (guessPos in codeList.indices) {
                if (isCorrectSymbolInWrongPosition(codePos, guessPos, codeList, guessList)) {
                    positionsUsed[codePos] = true
                    guessUsed[guessPos] = true
                    correctSymbols++
                }
            }
        }
        return correctSymbols
    }

    private fun isCorrectSymbolInWrongPosition(
        codePos: Int,
        guessPos: Int,
        codeList: List<String>,
        guessList: List<String>
    ): Boolean {
        return codePos != guessPos &&
                codeList[codePos] == guessList[guessPos] &&
                !positionsUsed[codePos] &&
                !guessUsed[guessPos]
    }


}
