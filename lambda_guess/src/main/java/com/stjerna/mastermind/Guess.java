package com.stjerna.mastermind;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stjerna.mastermind.core.DynamoDbGateway;
import com.stjerna.mastermind.core.ErrorResponse;
import com.stjerna.mastermind.core.LambdaResponse;
import com.stjerna.mastermind.core.MyPair;
import com.stjerna.mastermind.core.Success;
import com.stjerna.mastermind.core.Try;
import com.stjerna.mastermind.core.entity.MastermindGame;
import com.stjerna.mastermind.core.usecase.GuessInteractor;
import com.stjerna.mastermind.core.usecase.score.Score;

import java.util.ArrayList;

public class Guess implements RequestHandler<GuessRequest, LambdaResponse> {
    public LambdaResponse handleRequest(GuessRequest guessRequest, Context context) {

        Try<MastermindGame> result = new GuessInteractor(new DynamoDbGateway())
                .execute(guessRequest.gameID, guessRequest.guess);

        if (result instanceof Success) {
            MastermindGame game = (MastermindGame) ((Success) result).getValue();
            return getGuessResponse(game);
        } else {
            return new ErrorResponse("TBD");
        }
    }

    private GuessResponse getGuessResponse(MastermindGame game) {
        GuessResponse guessResponse = new GuessResponse();
        ArrayList<MyPair<String, Score>> guesses = game.getGuesses();
        Score score = guesses.get(guesses.size() - 1).getSecond();
        guessResponse.setCorrectColors(Integer.toString(score.getCorrectSymbol()));
        guessResponse.setCorrectPositions(Integer.toString(score.getCorrectPositions()));
        guessResponse.setFinished(game.isFinished());
        return guessResponse;
    }
}

class GuessResponse implements LambdaResponse {
    String correctColors;
    String correctPositions;
    boolean isFinished;

    public String getCorrectColors() {
        return correctColors;
    }

    public void setCorrectColors(String correctColors) {
        this.correctColors = correctColors;
    }

    public String getCorrectPositions() {
        return correctPositions;
    }

    public void setCorrectPositions(String correctPositions) {
        this.correctPositions = correctPositions;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}

class GuessRequest {
    String gameID;
    String guess;

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public GuessRequest() {

    }
}

