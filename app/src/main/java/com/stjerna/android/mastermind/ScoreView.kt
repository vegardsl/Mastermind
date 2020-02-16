package com.stjerna.android.mastermind

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.stjerna.mastermind.core.usecase.score.Score

class ScoreView(context: Context, attributeSet: AttributeSet? = null) :
    LinearLayout(context, attributeSet) {

    private val scoreItemList: List<View>

    private val guessText: TextView

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.guess_view, this)

        scoreItemList = listOf(
            view.findViewById(R.id.score_1_1),
            view.findViewById(R.id.score_1_2),
            view.findViewById(R.id.score_2_1),
            view.findViewById(R.id.score_2_2)
        )

        guessText = view.findViewById(R.id.guess_textView)
    }

    fun setGuess(guess: String) {
        guessText.text = guess
    }

    fun setScore(score: Score) {
        for (i in 0 until score.correctPositions) {
            scoreItemList[i].setBackgroundColor(
                ContextCompat.getColor(context, R.color.correctPosition)
            )
        }
        for (i in score.correctPositions until score.correctSymbol + score.correctPositions) {
            scoreItemList[i].setBackgroundColor(
                ContextCompat.getColor(context, R.color.correctSymbol)
            )
        }
    }
}