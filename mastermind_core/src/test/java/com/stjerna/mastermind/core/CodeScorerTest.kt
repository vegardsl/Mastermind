package com.stjerna.mastermind.core

import com.stjerna.mastermind_core.Score
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class CodeScorerTest {
    @Test
    fun createScorer() {
        CodeScorer("", "")
    }

    @Test
    fun getScore() {
        val score: Score = CodeScorer("", "").score
    }
}