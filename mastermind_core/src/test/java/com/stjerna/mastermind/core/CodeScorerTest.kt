package com.stjerna.mastermind.core

import com.stjerna.mastermind.core.usecase.score.CodeScorer
import com.stjerna.mastermind.core.usecase.score.Score
import org.junit.Test

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