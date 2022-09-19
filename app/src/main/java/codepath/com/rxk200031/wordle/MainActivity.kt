package codepath.com.rxk200031.wordle

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private var wordToGuess = FourLetterWordList.getRandomFourLetterWord()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvWordToGuess = findViewById<TextView>(R.id.tvWordToGuess)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val etUserGuess = findViewById<EditText>(R.id.etUserGuess)
        val tvUserGuess1 = findViewById<TextView>(R.id.tvUserGuess1)
        val tvUserGuess2 = findViewById<TextView>(R.id.tvUserGuess2)
        val tvUserGuess3 = findViewById<TextView>(R.id.tvUserGuess3)
        val tvGuess1CheckResults = findViewById<TextView>(R.id.tvGuess1CheckResults)
        val tvGuess2CheckResults = findViewById<TextView>(R.id.tvGuess2CheckResults)
        val tvGuess3CheckResults = findViewById<TextView>(R.id.tvGuess3CheckResults)
        btnReset.setBackgroundColor(Color.RED)
        tvWordToGuess.text = wordToGuess
        var guessesCount = 0
        btnSubmit.setOnClickListener {

            // could use a method to restart activity instead maybe?
            if (etUserGuess.text.isEmpty() || etUserGuess.text.toString().length != 4) {
                Toast.makeText(this, "Word length must be 4!", Toast.LENGTH_SHORT).show()
            } else {

                val result = checkGuess(etUserGuess.text.toString())
                guessesCount++
                when (guessesCount) {
                    1 -> {
                        tvUserGuess1.text = etUserGuess.text
                        tvGuess1CheckResults.text = result
                    }
                    2 -> {
                        tvUserGuess2.text = etUserGuess.text
                        tvGuess2CheckResults.text = result
                    }
                    3 -> {
                        tvUserGuess3.text = etUserGuess.text
                        tvGuess3CheckResults.text = result
                        if (result != "OOOO") Toast.makeText(this, "You've lost!", Toast.LENGTH_SHORT).show()
                        btnReset.isVisible = true
                        btnSubmit.isVisible = false
                    }
                }

                if (result == "OOOO") {
                    Toast.makeText(this, "Congratulations, you've won!", Toast.LENGTH_LONG).show()
                    tvWordToGuess.isVisible = true
                    btnReset.isVisible = true
                    btnSubmit.isVisible = false
                }
                hideKeyboard(etUserGuess)
                etUserGuess.text.clear()
            }
        }

        btnReset.setOnClickListener {
            tvUserGuess1.text = ""
            tvUserGuess2.text = ""
            tvUserGuess3.text = ""
            tvGuess1CheckResults.text = ""
            tvGuess2CheckResults.text = ""
            tvGuess3CheckResults.text = ""
            guessesCount = 0
            wordToGuess = FourLetterWordList.getRandomFourLetterWord()
            tvWordToGuess.text = wordToGuess
            btnReset.isVisible = false
            btnSubmit.isVisible = true
        }
    }

    private fun hideKeyboard(view: View) {
        view.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""

        for (i in 0..3) {
            result += if (guess[i].uppercaseChar() == wordToGuess[i].uppercaseChar()) {
                "O"
            } else if (guess[i].uppercaseChar() in wordToGuess.uppercase()) {
                "+"
            } else {
                "X"
            }
        }
        return result
    }
}