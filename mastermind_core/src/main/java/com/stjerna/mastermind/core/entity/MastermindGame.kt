package com.stjerna.mastermind.core.entity

import com.stjerna.mastermind_core.Score

data class MastermindGame(val id: String) {
    val guesses = arrayListOf<Pair<String, Score>>()
}