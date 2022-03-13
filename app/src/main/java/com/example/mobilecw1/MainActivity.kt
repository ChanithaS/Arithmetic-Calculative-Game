package com.example.mobilecw1

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newGameBtn = findViewById<Button>(R.id.NewGame)
        val aboutBtn = findViewById<Button>(R.id.About)

        newGameBtn.setOnClickListener{
            val game = Intent(this, GameActivity::class.java)
            startActivity(game)
        }

        aboutBtn.setOnClickListener{
            val window = PopupWindow(this)
            window.isFocusable
            val view = layoutInflater.inflate(R.layout.popup_window,null)
            window.contentView = view
            window.showAtLocation(view,Gravity.CENTER,0,0)

            // dismiss the popup window when touched
            view.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    window.dismiss()
                    return true
                }
            })
        }
    }
}