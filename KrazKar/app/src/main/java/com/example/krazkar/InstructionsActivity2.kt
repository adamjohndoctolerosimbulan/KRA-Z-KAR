package com.example.krazkar

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class InstructionsActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions2)

        // Set up click listener for the button to go back to MainActivity
        findViewById<ImageButton>(R.id.back2).setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_left,
                R.anim.slide_out_right

            )
            startActivity(intent, options.toBundle())
        }

        // Set up click listener for the button to go to InstructionsActivity2
        findViewById<ImageButton>(R.id.next2).setOnClickListener {
            val intent = Intent(this, InstructionsActivity3::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            startActivity(intent, options.toBundle())
        }
    }
}