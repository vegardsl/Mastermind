package com.stjerna.mastermind.core.usecase.newgame

import com.stjerna.mastermind.core.usecase.score.GameRuleSet
import kotlin.random.Random

class CodeGenerator {
    val generatedCode: String
    init {
        val randomIndices = List(GameRuleSet.codeSize) {
            Random.nextInt(0, GameRuleSet.symbolList.size)
        }

        val symbolList = mutableListOf<String>()
        randomIndices.forEach {
            symbolList.add(GameRuleSet.symbolList[it])
        }

        generatedCode = symbolList.joinToString(separator = "")
    }
}