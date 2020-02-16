package com.stjerna.android.mastermind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stjerna.mastermind.core.usecase.score.CodeScorer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
