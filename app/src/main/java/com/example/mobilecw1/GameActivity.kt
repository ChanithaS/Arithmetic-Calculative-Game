package com.example.mobilecw1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private val operators = setOf("+", "-", "*", "/")
    //list which holds the expression
    private var expression = mutableListOf<String>()
    var noOfTerms = 0
    var correctAns = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val (exp, ans) = expressionGen()
        println(exp + " sdsdfsdf " + ans)
    }

    private fun expressionGen(): Pair<String, Int>{
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
        var value = firstValue
        //setting up a count for getting the repeat count
        var count = 0;
        repeat(noOfTerms) {
            count++
            //adding a closing parenthesis if no of terms length is larger that 1
            if(count >= 2){
                expression.add(")")
            }
            //adding a random operator
            val operator = operators.random()
            expression.add(operator)
            //adding a random term
            val nextValues = randomVal(1, 20)
            expression.add(nextValues.toString())

            //calculating the answer while creating the equation step by step
            when (operator) {
                "+" -> value += nextValues
                "-" -> value -= nextValues
                "*" -> value *= nextValues
                else -> value /= nextValues
            }
        }
        //returning the answer and expression without commas
        return Pair(expression.joinToString(separator = " "), value)
    }

    private fun randomVal(startVal: Int, endVal: Int): Int {
        //var gen: java.util.Random = java.util.Random()
        return (startVal..endVal).random()
    }
}