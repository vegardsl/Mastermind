package com.stjerna.android.mastermind

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.CycleInterpolator
import android.widget.LinearLayout
import com.stjerna.mastermind.core.usecase.score.GameRuleSet
import kotlinx.android.synthetic.main.code_breaker_layout.view.*

class CodeBreakerView(context: Context, attributeSet: AttributeSet)
    : LinearLayout(context, attributeSet) {

    var onSentListener: ((String) -> Unit)? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.code_breaker_layout, this)

        a_button.setOnClickListener {
            addCharOrShake("A")
        }
        b_button.setOnClickListener {
            addCharOrShake("B")
        }
        c_button.setOnClickListener {
            addCharOrShake("C")
        }
        d_button.setOnClickListener {
            addCharOrShake("D")
        }
        e_button.setOnClickListener {
            addCharOrShake("E")
        }
        f_button.setOnClickListener {
            addCharOrShake("F")
        }
        undo_button.setOnClickListener {
            val currentText = code_guess_editText.text.toString()
            if (currentText.isBlank()) {
                shakeEditText()
            } else {
                code_guess_editText.setText(currentText.dropLast(1))
            }
            send_button.isEnabled = false
        }
        send_button.setOnClickListener {
            onSentListener?.invoke(code_guess_editText.text.toString())
        }
    }

    private fun addCharOrShake(s: String) {
        val currentText = code_guess_editText.text.toString()
        if (currentText.length >= GameRuleSet.codeSize) {
            shakeEditText()
        } else {
            addCharacter(s)
        }
    }

    private fun addCharacter(char: String) {
        val currentText = code_guess_editText.text.toString()
        val newText = "${currentText}$char"
        code_guess_editText.setText(newText)
        if (newText.length == GameRuleSet.codeSize) send_button.isEnabled = true
    }

    private fun shakeEditText() {
        code_guess_editText.animate()
            .setInterpolator(CycleInterpolator(3f))
            .translationX(5f)
            .setDuration(600L)
            .start()
    }

    fun setOnSendListener(listener: (String) -> Unit) {
        this.onSentListener = listener
    }
}