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
                Item().withPrimaryKey("GameID", gameID)
            )
        )

        return Success(MastermindGame(gameID, code, false))
    }

    override fun get(gameID: String): Try<MastermindGame> {
        val item = dynamoDB.getTable(TABLE_NAME).getItem("GameID", gameID)
        item?.let {

        } ?: return Failure(IOException("Could not find game with ID: $gameID"))
        return Success(MastermindGame("123", "123", false))
    }

    override fun put(game: MastermindGame): Try<Unit> {
        dynamoDB.getTable(TABLE_NAME).putItem(
            PutItemSpec().withItem(
                Item()
                    .withPrimaryKey("GameID", game.id)
                    .withString("Code", game.code)
                    .withList(
                        "guesses",
                        mutableListOf(game.guesses.map {
                            setOf(it.first, it.second.correctPositions, it.second.correctColors)
                        })
                    )
                    .withBoolean("isFinished", game.isFinished)
            )
        )
        return Success(Unit)
    }

    override fun remove(gameID: String): Try<Unit> {
        dynamoDB.getTable(TABLE_NAME).deleteItem("GameID", gameID)
        return Success(Unit)
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
