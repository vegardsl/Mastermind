package com.stjerna.android.mastermind

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.code_breaker_layout.*
import kotlinx.android.synthetic.main.code_breaker_layout.view.*

class CodeBreakerView(context: Context, attributeSet: AttributeSet)
    : LinearLayout(context, attributeSet) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.code_breaker_layout, this)

        a_button.setOnClickListener {
            val currentText = code_guess_editText.text.toString()
            code_guess_editText.setText("${currentText}A")
        }
        b_button.setOnClickListener {
            val currentText = code_guess_editText.text.toString()
            code_guess_editText.setText("${currentText}B")
        }
        c_button.setOnClickListener {
            val currentText = code_guess_editText.text.toString()
            code_guess_editText.setText("${currentText}C")
        }
        d_button.setOnClickListener {
            val currentText = code_guess_editText.text.toString()
            code_guess_editText.setText("${currentText}D")
        }
        e_button.setOnClickListener {
            val currentText = code_guess_editText.text.toString()
            code_guess_editText.setText("${currentText}E")
        }
        f_button.setOnClickListener {
            val currentText = code_guess_editText.text.toString()
            code_guess_editText.setText("${currentText}F")
        }
        undo_button.setOnClickListener {
            val currentText = code_guess_editText.text.toString()
            code_guess_editText.setText(currentText.dropLast(1))
        }
        send_button.setOnClickListener {
            Toast.makeText(context, "SEND", Toast.LENGTH_SHORT).show()
        }
    }
}