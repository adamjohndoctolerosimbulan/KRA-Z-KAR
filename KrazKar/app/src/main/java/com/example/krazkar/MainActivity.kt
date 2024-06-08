package com.example.krazkar

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Display the user name
        val userName = getUserName()
        if (userName != null) {
            val userNameTextView: TextView = findViewById(R.id.user_name_text_view)
            userNameTextView.text = "Hello, $userName!"
        } else {
            // If user name is null, launch EnterNameActivity
            launchEnterNameActivity()
            return
        }

        findViewById<ImageButton>(R.id.button_instruction).setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            startActivity(intent, options.toBundle())
        }

        // Set up button click listeners
        val carButtonIds = listOf(R.id.car1, R.id.car2, R.id.car3, R.id.car4, R.id.car5, R.id.car6, R.id.car7, R.id.car8)

        carButtonIds.forEach { buttonId ->
            findViewById<ImageButton>(buttonId).setOnClickListener {
                val carNumber = carButtonIds.indexOf(buttonId) + 1 // Get the car number based on button position
                val intent = Intent(this, Class.forName("com.example.krazkar.CarInfoActivity$carNumber"))
                val options = ActivityOptions.makeCustomAnimation(
                    this,
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
                startActivity(intent, options.toBundle())
            }
        }
    }

    private fun getUserName(): String? {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString("user_name", null)
    }

    private fun launchEnterNameActivity() {
        val intent = Intent(this, EnterNameActivity::class.java)
        startActivity(intent)
    }
}
