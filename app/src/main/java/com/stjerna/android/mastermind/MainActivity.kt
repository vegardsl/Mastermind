package com.stjerna.android.mastermind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stjerna.mastermind.core.CodeScorer

class MainActivity : AppCompatActivity() {
    val scorer = CodeScorer("", "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
