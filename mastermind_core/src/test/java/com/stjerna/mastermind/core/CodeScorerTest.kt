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

    private fun assertScoreIsCorrect(
        correctPosition: Int, correctSymbol: Int,
        code: String, guess: String
    ) {
        val score: Score = CodeScorer(code, guess).score
        assertEquals(Score(correctPosition, correctSymbol), score)
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
    fun getScore_noMatches() {
        val score: Score = CodeScorer("ABCD", "EEEE").score
        assertEquals(Score(0, 0), score)
    }

    @Test
    fun getScore_correctSymbol_wrongPosition() {
        assertScoreIsCorrect(correctPosition = 0, correctSymbol = 1, code = "ABCD", guess = "EEEA")
        assertScoreIsCorrect(correctPosition = 0, correctSymbol = 1, code = "ABCD", guess = "EEAE")
        assertScoreIsCorrect(correctPosition = 0, correctSymbol = 2, code = "ABCD", guess = "EAEB")
        assertScoreIsCorrect(correctPosition = 0, correctSymbol = 4, code = "ABCD", guess = "DCBA")
    }

    @Test
    fun getScore_correctPosition() {
        assertScoreIsCorrect(correctPosition = 1, correctSymbol = 0, code = "ABCD", guess = "AEEE")
        assertScoreIsCorrect(correctPosition = 2, correctSymbol = 2, code = "DCBA", guess = "DCAB")
        assertScoreIsCorrect(correctPosition = 4, correctSymbol = 0, code = "DCBA", guess = "DCBA")
        assertScoreIsCorrect(correctPosition = 1, correctSymbol = 1, code = "FEDC", guess = "ABCC")
    }

    @Test
    fun isPerfectScore() {
        assertTrue(CodeScorer("DCBA", "DCBA").isPerfectScore)
        assertFalse(CodeScorer("ABCD", "DCBA").isPerfectScore)
        assertFalse(CodeScorer("ABCD", "EEEA").isPerfectScore)
    }
}