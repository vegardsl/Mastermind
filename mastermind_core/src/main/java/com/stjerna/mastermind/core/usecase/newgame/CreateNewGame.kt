package com.stjerna.mastermind.core.usecase.newgame

import com.stjerna.mastermind.core.entity.GameGateway
import java.util.*

class CreateNewGame(
    private val presenter: NewGamePresenter,
    private val storage: GameGateway
) {
    fun execute() {
        storage.create(UUID.randomUUID().toString()) { result ->
            presenter.present(result)
        }
    }

}