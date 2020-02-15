package com.stjerna.mastermind.core.entity

import com.stjerna.mastermind.core.MyPair
import com.stjerna.mastermind.core.usecase.score.Score

data class MastermindGame(val id: String, val code: String, var isFinished: Boolean) {
    val guesses = arrayListOf<MyPair<String, Score>>()
}