package com.stjerna.mastermind;

import com.amazonaws.services.lambda.runtime.Context;
import com.stjerna.mastermind.core.DynamoDbGateway;
import com.stjerna.mastermind.core.Success;
import com.stjerna.mastermind.core.Try;
import com.stjerna.mastermind.core.entity.MastermindGame;
import com.stjerna.mastermind.core.usecase.newgame.CreateNewGame;

public class Guess {
    public String handleRequest(Void any, Context context) {

        Try<MastermindGame> result = new CreateNewGame(new DynamoDbGateway()).execute();

        if (result instanceof Success) {
            MastermindGame game = (MastermindGame) ((Success) result).getValue();
            return game.getId();
        } else {
            return "NewGame ERROR";
        }

    }
}
