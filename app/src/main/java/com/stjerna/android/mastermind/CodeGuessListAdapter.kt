package com.stjerna.android.mastermind

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stjerna.android.mastermind.cloud.GuessResponse
import com.stjerna.mastermind.core.usecase.score.Score

class CodeGuessListAdapter : RecyclerView.Adapter<CodeGuessListAdapter.ViewHolder>() {

    private var attempts = mutableListOf<GuessResponse>()

    fun addAttempt(guess: GuessResponse) {
        attempts.add(guess)
        attempts.sortBy { it.attemptNumber }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ScoreView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return attempts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.update(attempts[position])
    }

    class ViewHolder(private val scoreView: ScoreView) : RecyclerView.ViewHolder(scoreView) {
        fun update(guessResponse: GuessResponse) {
            val attemptNumber: Int = guessResponse.attemptNumber
            val guess: String = guessResponse.guess

            scoreView.setScore(
                Score(
                    guessResponse.correctPositions.toInt(),
                    guessResponse.correctColors.toInt()
                )
            )
            scoreView.setGuess(guessResponse.guess)
        }
    }
}
