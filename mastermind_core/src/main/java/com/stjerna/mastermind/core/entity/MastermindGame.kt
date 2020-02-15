package com.stjerna.mastermind.core.entity

import com.stjerna.mastermind.core.usecase.score.Score

data class MastermindGame(val id: String, val code: String, val isFinished: Boolean) {
    val guesses = arrayListOf<Pair<String, Score>>()
}