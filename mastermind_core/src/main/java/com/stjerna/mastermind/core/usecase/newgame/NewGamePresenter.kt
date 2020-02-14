package com.stjerna.mastermind.core.usecase.newgame

import com.stjerna.mastermind.core.Try
import com.stjerna.mastermind.core.entity.MastermindGame

interface NewGamePresenter {
    fun present(result: Try<MastermindGame>)
}
