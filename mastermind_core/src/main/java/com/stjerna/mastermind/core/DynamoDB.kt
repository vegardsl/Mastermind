package com.stjerna.mastermind.core

import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec
import com.stjerna.mastermind.core.DynamoDbConfig.REGION
import com.stjerna.mastermind.core.DynamoDbConfig.TABLE_NAME
import com.stjerna.mastermind.core.entity.GameGateway
import com.stjerna.mastermind.core.entity.MastermindGame
import com.stjerna.mastermind.core.usecase.score.Score
import java.io.IOException


object DynamoDbConfig {
    const val TABLE_NAME = "mastermind-game"
    val REGION = Regions.US_EAST_1
}

class DynamoDbGateway : GameGateway {

    private val dynamoDB: DynamoDB

    init {
        val client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build()
        dynamoDB = DynamoDB(client)
    }

    override fun create(gameID: String, code: String): Try<MastermindGame> {
        dynamoDB.getTable(TABLE_NAME).putItem(
            PutItemSpec().withItem(
                Item()
                    .withPrimaryKey(GAME_ID, gameID)
                    .withString(CODE, code)
                    .withBoolean(IS_FINISHED, false)
            )
        )

        return Success(MastermindGame(gameID, code, false))
    }

    override fun get(gameID: String): Try<MastermindGame> {
        val item = dynamoDB.getTable(TABLE_NAME).getItem(GAME_ID, gameID)
        item?.let {
            val game = MastermindGame(
                id = it.getString(GAME_ID),
                code = it.getString(CODE),
                isFinished = it.getBOOL(IS_FINISHED)
            )

            it.getList<List<String>>(GUESSES)?.let { list ->
                list.forEach { guess ->
                    require(guess.size == 3)
                    game.guesses.add(MyPair(guess[0], Score(guess[1].toInt(), guess[2].toInt())))
                }
            }

            return Success(game)
        } ?: return Failure(IOException("Could not find game with ID: $gameID"))
    }

    override fun put(game: MastermindGame): Try<Unit> {
        val guessList = mutableListOf<List<String>>()
        game.guesses.forEach {
            guessList.add(
                listOf(
                    it.first,
                    it.second.correctPositions.toString(),
                    it.second.correctSymbol.toString()
                )
            )
        }
        dynamoDB.getTable(TABLE_NAME).putItem(
            PutItemSpec().withItem(
                Item()
                    .withPrimaryKey(GAME_ID, game.id)
                    .withString(CODE, game.code)
                    .withList(
                        GUESSES,
                        guessList
                    )
                    .withBoolean(IS_FINISHED, game.isFinished)
            )
        )
        return Success(Unit)
    }

    override fun remove(gameID: String): Try<Unit> {
        dynamoDB.getTable(TABLE_NAME).deleteItem(GAME_ID, gameID)
        return Success(Unit)
    }

    companion object {
        const val GAME_ID = "GameID"
        const val CODE = "Code"
        const val IS_FINISHED = "isFinished"
        const val GUESSES = "guesses"
    }
}

// {
//         "Code": {
//         "S": "ABCD"
//         },
//         "GameID": {
//         "S": "ABC"
//         },
//         "guesses": {
//         "L": [
//         {
//         "SS": [
//         "ABCD",
//         "1",
//         "2"
//         ]
//         }
//         ]
//         },
//         "isFinished": {
//         "BOOL": true
//         }
//         }
