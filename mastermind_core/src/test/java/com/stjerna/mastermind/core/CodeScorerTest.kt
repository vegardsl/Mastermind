package com.stjerna.mastermind.core

import com.stjerna.mastermind.core.usecase.score.CodeScorer
import com.stjerna.mastermind.core.usecase.score.Score
import org.junit.Assert.*
import org.junit.Test

internal class CodeScorerTest {

    private fun assertIllegalArgumentExceptionIsThrown(code: String, guess: String) {
        try {
            CodeScorer(code, guess)
            fail()
        } catch (e: Exception) {
            assertTrue(e is IllegalArgumentException)
        }
    }

    @Test
    fun createScorer_illegalArguments() {
        assertIllegalArgumentExceptionIsThrown("", "")
        assertIllegalArgumentExceptionIsThrown("ABC", "ABC")
        assertIllegalArgumentExceptionIsThrown("ABCDE", "ABCDE")
        assertIllegalArgumentExceptionIsThrown("XYZT", "XYZT")
        assertIllegalArgumentExceptionIsThrown("ABCX", "ABCX")
    }

    @Test
    fun createScorer() {
        val scorer = try {
            CodeScorer("ABCD", "ABCD")
        } catch (e: Exception) {
            fail()
        }
        assertNotNull(scorer)
    }

    @Test
    fun getScore() {
        //val score: Score = CodeScorer("", "").score
    }
}