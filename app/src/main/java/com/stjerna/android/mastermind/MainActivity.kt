package com.stjerna.android.mastermind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stjerna.mastermind.core.usecase.score.CodeScorer
import com.stjerna.mastermind.core.usecase.score.Score

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scoreView: ScoreView = findViewById(R.id.scoreView)
        scoreView.setGuess("ABCD")
        scoreView.setScore(Score(1,2))
    }
}
