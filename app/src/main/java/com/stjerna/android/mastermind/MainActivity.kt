package com.stjerna.android.mastermind

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stjerna.mastermind.core.Failure
import com.stjerna.mastermind.core.Success
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val codeGuessListAdapter = CodeGuessListAdapter()
    private val repository = MastermindRepository()
    private var gameID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        code_guess_list.itemAnimator = null
        code_guess_list.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        code_guess_list.adapter = codeGuessListAdapter
        codeGuessListAdapter.notifyDataSetChanged()

        new_game_button.setOnClickListener {
            repository.newGame {
                when (it) {
                    is Success -> {
                        runOnUiThread { setInGameState(it) }
                    }
                    is Failure -> Toast.makeText(
                        this,
                        "Failed to start new game.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        code_breaker_view.setOnSendListener {
            gameID?.let { nonNullGameID ->
                repository.makeGuess(nonNullGameID, it) {
                    when (it) {
                        is Success -> {
                            runOnUiThread {
                                codeGuessListAdapter.addAttempt(it.value)
                                if (it.value.finished) {
                                    Toast.makeText(this, "YOU WON!", Toast.LENGTH_LONG).show()
                                    setNoGameState()
                                }
                            }
                        }
                        is Failure -> Toast.makeText(
                            this,
                            "Failed to make guess.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setInGameState(it: Success<GameID>) {
        gameID = it.value
        new_game_button.visibility = View.GONE
        code_breaker_view.visibility = View.VISIBLE
        code_guess_list.visibility = View.VISIBLE
    }

    private fun setNoGameState() {
        gameID = null
        new_game_button.visibility = View.VISIBLE
        code_breaker_view.visibility = View.GONE
        code_guess_list.visibility = View.GONE
    }
}
