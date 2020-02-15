package com.stjerna.mastermind.aws_lambda.newgame;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stjerna.mastermind.core.usecase.score.CodeScorer;
import com.stjerna.mastermind.core.usecase.score.Score;

import java.util.Locale;

public class NewGame implements RequestHandler<String, String> {
    public String handleRequest(String name, Context context) {
        Score score = new CodeScorer("", "").getScore();
        return String.format(
                Locale.getDefault(),
                "Hello %s. Your score is : %d",
                name,
                score.getCorrectSymbol()
        );
    }
}
