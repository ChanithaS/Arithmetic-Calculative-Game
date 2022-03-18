package com.example.mobilecw1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    //variables for elements
    private lateinit var newGameBtn : Button
    private lateinit var aboutBtn : Button
    private lateinit var heading1 : TextView
    private lateinit var heading2 : TextView
    private lateinit var quote : TextView
    private lateinit var booImage : ImageView
    private lateinit var operator1 : ImageView
    private lateinit var operator2 : ImageView
    private lateinit var operator3 : ImageView
    private lateinit var operator4 : ImageView
    private lateinit var operator5 : ImageView
    private lateinit var operator6 : ImageView
    private lateinit var operator7 : ImageView
    private lateinit var operator8 : ImageView
    var isVisiblePopUp = false
    var active = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //hiding the action bar
        supportActionBar?.hide()

        //initializing elements
        newGameBtn = findViewById(R.id.NewGame)
        aboutBtn = findViewById(R.id.About)
        heading1 = findViewById(R.id.textView2)
        heading2 = findViewById(R.id.textView3)
        quote = findViewById(R.id.textView4)
        booImage = findViewById(R.id.imageView)
        operator1 = findViewById(R.id.operimg1)
        operator2 = findViewById(R.id.operimg2)
        operator3 = findViewById(R.id.operimg3)
        operator4 = findViewById(R.id.operimg4)
        operator5 = findViewById(R.id.operimg5)
        operator6 = findViewById(R.id.operimg6)
        operator7 = findViewById(R.id.operimg7)
        operator8 = findViewById(R.id.operimg8)

        startAnim()

        //action listener for new game button to start the game
        newGameBtn.setOnClickListener{
            val game = Intent(this, GameActivity::class.java)
            startActivity(game)
        }
        //action listener for about button                             //https://stackoverflow.com/questions/5944987/how-to-create-a-popup-window-popupwindow-in-android
        aboutBtn.setOnClickListener{
            if (!active){
                popUpWindow()
                isVisiblePopUp = true
            }
        }
    }

    private fun popUpWindow(){
        active = true
        //new popup window
        val window = PopupWindow(this)
        window.isFocusable
        val view = layoutInflater.inflate(R.layout.popup_window,null)
        window.contentView = view

        //adding fade in/out animation
        val fadeIn = Fade()
        fadeIn.duration = 400
        val fadeOut = Fade()
        fadeOut.duration = 400
        //adding animation to pop up window if in range of version supproting the animation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            window.enterTransition = fadeIn
            window.exitTransition = fadeOut
        }

        //centering the pop up window
        window.showAtLocation(view,Gravity.CENTER,0,0)

        // dismiss the popup window when touched
        view.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                window.dismiss()
                active = false
                return true
            }
        })
    }

    //animation
    private fun startAnim(){
        //leading animation
        val randomMove = AnimationUtils.loadAnimation(this, R.anim.move_around)
        val randomMove1 = AnimationUtils.loadAnimation(this, R.anim.move_around1)
        val randomMove2 = AnimationUtils.loadAnimation(this, R.anim.move_around2)
        val randomMove3 = AnimationUtils.loadAnimation(this, R.anim.move_around3)
        val upDown = AnimationUtils.loadAnimation(this, R.anim.updown)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein)
        //starting animation
        operator1.startAnimation(randomMove3)
        operator2.startAnimation(randomMove3)
        operator3.startAnimation(randomMove1)
        operator4.startAnimation(randomMove2)
        operator5.startAnimation(randomMove3)
        operator6.startAnimation(randomMove1)
        operator7.startAnimation(randomMove2)
        operator8.startAnimation(randomMove)
        booImage.startAnimation(upDown)
        newGameBtn.startAnimation(fadeIn)
        aboutBtn.startAnimation(fadeIn)
    }

//                    var translationX = (-100..100).random()
//                    var translationY = (-100..100).random()
//
//
//                    var anim = TranslateAnimation( 0f, translationX.toFloat() , 0f, translationY.toFloat() ); //Use current view position instead of `currentX` and `currentY`
//                    anim.duration = 1000;
//                    //anim.fillAfter = true;
//                    newGameBtn.startAnimation(anim)
//                    var anim1 = TranslateAnimation( translationX.toFloat(), 200f , translationY.toFloat(), 200f ); //Use current view position instead of `currentX` and `currentY`
//                    anim.duration = 1000;
//                    //anim.fillAfter = true;
//                    newGameBtn.startAnimation(anim1)
}