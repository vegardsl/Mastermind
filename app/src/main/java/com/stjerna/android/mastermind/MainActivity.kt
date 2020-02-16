package com.stjerna.android.mastermind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stjerna.mastermind.core.usecase.score.CodeScorer
import com.stjerna.mastermind.core.usecase.score.Score
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val codeGuessListAdapter = CodeGuessListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // val scoreView: ScoreView = findViewById(R.id.scoreView)
        // scoreView.setGuess("ABCD")
        // scoreView.setScore(Score(1,2))

        code_guess_list.itemAnimator = null
        code_guess_list.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        code_guess_list.adapter = codeGuessListAdapter
        codeGuessListAdapter.notifyDataSetChanged()
    }
}
