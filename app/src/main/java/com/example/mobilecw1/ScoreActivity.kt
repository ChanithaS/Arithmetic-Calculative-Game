package com.example.mobilecw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    private var correct = 0
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        supportActionBar?.hide()

        //initializing elements
        val resultTxt = findViewById<TextView>(R.id.score)
        val quote = findViewById<TextView>(R.id.goodOrBad)
        val star1 = findViewById<ImageView>(R.id.star1)
        val star2 = findViewById<ImageView>(R.id.star2)
        val star3 = findViewById<ImageView>(R.id.star3)
        val ghost = findViewById<ImageView>(R.id.booCharacter)
        //loading animation
        val upDown = AnimationUtils.loadAnimation(this, R.anim.updown)
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val blink = AnimationUtils.loadAnimation(this, R.anim.blink_anim)

        //setting animation to 3 stars
        star1.startAnimation(bounce)
        star2.startAnimation(bounce)
        star3.startAnimation(bounce)

        //getting data from Game Activity onCreate
        correct = intent.getStringExtra("correct")!!.toInt()
        total = intent.getStringExtra("total")!!.toInt()

        //setting score to the text view
        resultTxt.text = " You Got $correct Correct of $total"

        //calculating the percentage to show the performance
        var percentage = 0
        if (total > 0){
            percentage = (100/ total) * correct
        }

        //showing stars , quotes and animation according to the performance
        when {
            percentage >= 75 -> {
                //3 stars
                star1.setImageResource(R.drawable.star)
                star2.setImageResource(R.drawable.star)
                star3.setImageResource(R.drawable.star)
                ghost.startAnimation(fadeOut)
                quote.text = "Excellent Job!! You scared the ghost away"
            }
            percentage in 50..74 -> {
                //2 stars
                star1.setImageResource(R.drawable.star)
                star2.setImageResource(R.drawable.star)
                ghost.startAnimation(blink)
                quote.text = "Good Job!! Little more to scare the ghost"
            }
            percentage in 11..49 -> {
                //1 stars
                star1.setImageResource(R.drawable.star)
                ghost.startAnimation(upDown)
                quote.text = "Try Harder! The ghost didn't get scared"
            }
            else -> {
                ghost.startAnimation(upDown)
                quote.text = "Don't be lazy, Try Harder"
            }
        }

    }

    /**
     *overriding back function so returning to main activity rather than game activity
     */
    override fun onBackPressed() {             //https://stackoverflow.com/questions/18404271/android-back-button-to-specific-activity#:~:text=use%20action%20bar%20on%20click,back%20to%20the%20previous%20activity.
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    /**
     *saving the correct, total values so doesn't reset when changing layout mode
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("correct", correct)
        outState.putInt("total", total)
    }

    /**
     *restoring the data after the layout change
     * @param savedInstanceState
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        correct = savedInstanceState.getInt("correct")
        total = savedInstanceState.getInt("total")
    }
}