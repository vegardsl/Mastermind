package com.stjerna.android.mastermind

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stjerna.android.mastermind.AppConfig.GAME_ID_EXTRA
import com.stjerna.android.mastermind.cloud.GuessResponse
import com.stjerna.mastermind.core.Failure
import com.stjerna.mastermind.core.Success
import kotlinx.android.synthetic.main.activity_main.*

class GameActivity : AppCompatActivity() {

    private val codeGuessListAdapter = CodeGuessListAdapter()
    private val repository = MastermindRepository()
    private var gameID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameID = intent.getStringExtra(GAME_ID_EXTRA)

        code_guess_list.itemAnimator = null
        code_guess_list.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )
        code_guess_list.adapter = codeGuessListAdapter

        code_breaker_view.setOnSendListener {
            gameID?.let { nonNullGameID ->
                repository.makeGuess(nonNullGameID, it) {
                    when (it) {
                        is Success -> runOnUiThread { handleGuessResult(it.value) }
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

    private fun handleGuessResult(guessResponse: GuessResponse) {
        codeGuessListAdapter.addAttempt(guessResponse)
        code_guess_list.scrollToPosition(codeGuessListAdapter.itemCount - 1)
        if (guessResponse.finished) {
            Toast.makeText(this, "YOU WON!", Toast.LENGTH_LONG).show()
            finish()
        }
    }


}
