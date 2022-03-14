package com.example.mobilecw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    private var correct = ""
    private var total = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        supportActionBar?.hide()

        val resultTxt = findViewById<TextView>(R.id.score)
        val quote = findViewById<TextView>(R.id.goodOrBad)
        val star1 = findViewById<ImageView>(R.id.star1)
        val star2 = findViewById<ImageView>(R.id.star2)
        val star3 = findViewById<ImageView>(R.id.star3)
        val ghost = findViewById<ImageView>(R.id.booCharacter)
        val upDown = AnimationUtils.loadAnimation(this, R.anim.updown)
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout)

        correct = intent.getStringExtra("correct")!!
        total = intent.getStringExtra("total")!!

        resultTxt.text = " You Got $correct Correct of $total"

        val percentage = (100/total!!.toInt()) * correct!!.toInt()
        if (percentage >= 75){
            star1.setImageResource(R.drawable.star)
            star2.setImageResource(R.drawable.star)
            star3.setImageResource(R.drawable.star)
            ghost.startAnimation(fadeOut)
            quote.text = "Excellent Job!! You scared the ghost away"
        }else if (percentage in 50..74){
            star1.setImageResource(R.drawable.star)
            star2.setImageResource(R.drawable.star)
            ghost.startAnimation(upDown)
            quote.text = "Good Job!! Little more to scare the ghost"
        }else if (percentage < 50){
            star1.setImageResource(R.drawable.star)
            ghost.startAnimation(upDown)
            quote.text = "Try Harder! The ghost didn't get scared"
        }

    }
    //when going back returning to main activity rather than game activity
    override fun onBackPressed() {             //https://stackoverflow.com/questions/18404271/android-back-button-to-specific-activity#:~:text=use%20action%20bar%20on%20click,back%20to%20the%20previous%20activity.
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("correct", correct)
        outState.putString("total", total)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        correct = savedInstanceState.getString("correct")!!
        total = savedInstanceState.getString("total")!!
    }
}