package com.stjerna.mastermind.core.usecase.newgame

import com.stjerna.mastermind.core.usecase.score.GameRuleSet
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class CodeGeneratorTest {
    @Test
    fun generateRandomCode() {
        val code = CodeGenerator().generatedCode
        assertEquals(GameRuleSet.codeSize, code.length)
    }

    @Test
    fun generateCodesAreDifferent() {
        val code1 = CodeGenerator().generatedCode
        val code2 = CodeGenerator().generatedCode
        assertNotEquals(code1, code2)
    }
}