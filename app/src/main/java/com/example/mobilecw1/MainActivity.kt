package com.example.mobilecw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newGameBtn = findViewById<Button>(R.id.NewGame)
        val aboutBtn = findViewById<Button>(R.id.About)

        newGameBtn.setOnClickListener{
            val game = Intent(this, GameActivity::class.java)
            game.putExtra("reset", 0)
            startActivity(game)
        }
        aboutBtn.setOnClickListener{

        }
    }


}