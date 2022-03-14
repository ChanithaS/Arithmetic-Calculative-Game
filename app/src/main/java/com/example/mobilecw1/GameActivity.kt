package com.example.mobilecw1

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var expressionText : TextView
    private lateinit var expression2Text : TextView
    private lateinit var answerText : TextView
    private lateinit var timerText : TextView
    private lateinit var correctSound : MediaPlayer
    private lateinit var wrongSound : MediaPlayer

    private val operators = setOf("+", "-", "*", "/")
    //storing the two values for the 2 expressions
    var exp1Ans = 0
    var exp2Ans = 0
    var expression = ""
    var expression2 = ""

    var timeLeft:Long = 15000
    var endTime:Long = 0

    var totalQuestions = 0
    var correctAns = 0
    var consecutiveCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.hide()
        timer()

        val greaterBtn = findViewById<Button>(R.id.greater)
        val lessBtn = findViewById<Button>(R.id.lesser)
        val equalBtn = findViewById<Button>(R.id.equal)
        expressionText = findViewById(R.id.expression1)
        expression2Text = findViewById(R.id.expression2)
        answerText = findViewById(R.id.answer)
        timerText = findViewById(R.id.timer)

        correctSound = MediaPlayer.create(this, R.raw.correct_sound)
        wrongSound = MediaPlayer.create(this, R.raw.wrong_sound)

        greaterBtn.setOnClickListener{
            check(1)
        }
        lessBtn.setOnClickListener{
            check(2)
        }
        equalBtn.setOnClickListener{
            check(3)
        }
        newQuestion()
        animation(greaterBtn, lessBtn, equalBtn)
    }

    private fun newQuestion(){
        //for two expressions
        for (i in 0..1){
            val (exp, ans) = expressionGen()
            //assigning values for the 2 expressions
            if (i == 0){
                exp1Ans = ans
                expression = exp
                expressionText.text = expression
            }else{
                exp2Ans = ans
                expression2 = exp
                expression2Text.text = expression2

            }
        }
        println(exp1Ans)
        println(exp2Ans)
    }

    private fun expressionGen(): Pair<String, Int>{
        //list which holds the expression
        val expression = mutableListOf<String>()
        var noOfTerms = 0
        //creating a random number of terms which can be in the expression
        noOfTerms = Random.nextInt(1, 4)

        //adding the start parenthesis according to no of terms
        if (noOfTerms >= 2){
            repeat(noOfTerms - 1){
                expression.add("(")
            }
        }
        //adding the first random term
        val firstValue = randomVal(1, 20)
        expression.add(firstValue.toString())
        //setting up for calculating the answer
        var answer = firstValue.toDouble()

        //generating a operator and a value for no of terms
        for(i in 1..noOfTerms) {
            val storeAns = answer
            //adding a closing parenthesis if no of terms length is larger that 1
            if(i >= 2){
                expression.add(")")
            }

            //creating a random operator
            var operator = operators.random()
            //creating a random term
            var nextValues = randomVal(1, 20)
            //calculating the answer while creating the equation step by step
            when (operator) {
                "+" -> answer += nextValues.toDouble()
                "-" -> answer -= nextValues.toDouble()
                "*" -> answer *= nextValues.toDouble()
                else -> answer /= nextValues.toDouble()
            }

            //generating new terms while the sub expression is a whole number and is between 0 to 100
            var valid = false
            while (!valid){
                //(value % 1.0 != 0.0) &&  && value > 0.0 && value <= 100.0
                if (answer - answer.toInt() == 0.0 && answer > 0.0 && answer <= 100.0){            //https://stackoverflow.com/questions/45422290/checking-if-the-output-of-a-calculation-is-a-whole-number/45422616#45422616
                    //ending the loop
                    valid = true
                }else{
                    answer = storeAns
                    //creating a random operator
                    operator = operators.random()
                    //creating a random term
                    nextValues = randomVal(1, 20)

                    //calculating the answer while creating the equation step by step
                    when (operator) {
                        "+" -> answer += nextValues.toDouble()
                        "-" -> answer -= nextValues.toDouble()
                        "*" -> answer *= nextValues.toDouble()
                        else -> answer /= nextValues.toDouble()
                    }
                }
            }
            //adding the generated values to the array
            expression.add(operator)
            expression.add(nextValues.toString())
        }

        //returning the answer and expression without commas
        return Pair(expression.joinToString(separator = ""), answer.toInt())
    }

    private fun check(buttonNo: Int){
        totalQuestions++
        if (exp1Ans > exp2Ans && buttonNo == 1){
            correctWrong(1)
        }else if (exp1Ans < exp2Ans && buttonNo == 2){
            correctWrong(1)
        }else if(exp1Ans == exp2Ans && buttonNo == 3){
            correctWrong(1)
        }else{
            correctWrong(2)
        }

        if (consecutiveCount == 5){
            consecutiveCount = 0
            timeLeft += 10000
        }
        newQuestion()
    }

    private fun timer(){
        val zoomInOut = AnimationUtils.loadAnimation(this, R.anim.zoomin)
        //time to repeat every sec
        val period:Long  = 1000

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                timeLeft -= 1000
                endTime = System.currentTimeMillis() + timeLeft
                if (timeLeft.toInt() < 10000){
                    timerText.startAnimation(zoomInOut)
                }
                runOnUiThread( Runnable()
                {
                    val minutes = (timeLeft/1000)/60
                    val seconds = (timeLeft/1000)%60

                    timerText.text = String.format("%02d : %02d", minutes, seconds)
                    if (timeLeft.toInt() == 0){
                        finishView()
                    }
                })
            }
        }, 0, period)
    }

    override fun onSaveInstanceState(outState: Bundle) {         //https://www.youtube.com/watch?v=LMYQS1dqfo8
        super.onSaveInstanceState(outState)
        outState.putLong("timeLeft", timeLeft)
        outState.putLong("endTime", endTime)
        outState.putString("expression1", expression)
        outState.putString("expression2", expression2)
        outState.putInt("answer1", exp1Ans)
        outState.putInt("answer2", exp2Ans)
        outState.putInt("consecutiveCount", consecutiveCount)
        outState.putInt("correctAnswers", correctAns)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        timeLeft = savedInstanceState.getLong("timeLeft")
        endTime = savedInstanceState.getLong("endTime")
        expression = savedInstanceState.getString("expression1")!!
        expression2 = savedInstanceState.getString("expression2")!!
        exp1Ans = savedInstanceState.getInt("answer1")
        exp2Ans = savedInstanceState.getInt("answer2")
        consecutiveCount = savedInstanceState.getInt("consecutiveCount")
        correctAns = savedInstanceState.getInt("correctAnswers")

        expressionText.text = expression
        expression2Text.text = expression2

        timeLeft = endTime - System.currentTimeMillis()
    }

    private fun finishView(){
        val scoreIntent = Intent(this, ScoreActivity::class.java)
        scoreIntent.putExtra("correct", correctAns.toString())
        scoreIntent.putExtra("total", totalQuestions.toString())
        startActivity(scoreIntent)
    }

    private fun correctWrong(int : Int){
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        if (int == 1){
            correctAns++
            consecutiveCount++
            answerText.text = "CORRECT"
            answerText.setTextColor(Color.parseColor("#00FF00"))
            correctSound.start()
            answerText.startAnimation(fadeOut)
        }else{
            answerText.text = "Wrong"
            answerText.setTextColor(Color.parseColor("#FF0000"))
            wrongSound.start()
            answerText.startAnimation(fadeOut)
        }
    }

    private fun randomVal(startVal: Int, endVal: Int): Int {
        return (startVal..endVal).random()
    }

    private fun animation(greaterBtn: Button, lessBtn: Button, equalBtn: Button) {
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein)

        greaterBtn.startAnimation(fadeIn)
        lessBtn.startAnimation(fadeIn)
        equalBtn.startAnimation(fadeIn)
    }
}