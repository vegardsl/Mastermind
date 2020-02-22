package com.stjerna.android.mastermind

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.stjerna.android.mastermind.AppConfig.GAME_ID_EXTRA
import com.stjerna.mastermind.core.Failure
import com.stjerna.mastermind.core.Success
import kotlinx.android.synthetic.main.activity_new_game.*

class NewGameActivity : AppCompatActivity() {

    private val repository = MastermindRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        new_game_button.setOnClickListener {
            repository.newGame {
                when (it) {
                    is Success -> {
                        runOnUiThread { navigateToGameScreen(it.value) }
                    }
                    is Failure -> Toast.makeText(
                        this,
                        "Failed to start new game.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun navigateToGameScreen(gameId: String) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(GAME_ID_EXTRA, gameId)
        startActivity(intent)
    }
}