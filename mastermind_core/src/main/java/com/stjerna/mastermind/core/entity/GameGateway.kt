package com.stjerna.mastermind.core.entity

import com.stjerna.mastermind.core.Try

interface GameGateway {
    fun create(gameID: String, code: String): Try<MastermindGame>
    fun get(gameID: String): Try<MastermindGame>
    fun put(game: MastermindGame): Try<Unit>
    fun remove(gameID: String): Try<Unit>
}

// interface GameGateway {
//     fun create(gameID: String, code: String, result: (Try<MastermindGame>) -> Unit)
//     fun get(gameID: String, result: (Try<MastermindGame>) -> Unit)
//     fun put(game: MastermindGame, result: (Try<Unit>) -> Unit)
//     fun remove(gameID: String, result: (Try<Unit>) -> Unit)
// }

open class StubbedGameGateway: GameGateway {
    override fun create(gameID: String, code: String): Try<MastermindGame> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(gameID: String): Try<MastermindGame> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun put(game: MastermindGame): Try<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(gameID: String): Try<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
