package com.example.mobilecw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val resultTxt = findViewById<TextView>(R.id.score)

        val correct =intent.getStringExtra("correct")
        val total = intent.getStringExtra("total")

        resultTxt.text = " You Got $correct Correct of $total"
    }
    //when going back returning to main activity rather than game activity
    override fun onBackPressed() {             //https://stackoverflow.com/questions/18404271/android-back-button-to-specific-activity#:~:text=use%20action%20bar%20on%20click,back%20to%20the%20previous%20activity.
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}