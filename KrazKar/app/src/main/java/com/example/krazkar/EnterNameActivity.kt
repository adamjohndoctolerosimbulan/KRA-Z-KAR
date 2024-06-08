package com.example.krazkar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide

class EnterNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_enter_name)

        showGif()

        // Check if user name is already saved
        val userName = getUserName()
        if (userName != null) {
            navigateToMainActivity()
            return
        }

        val nameEditText: EditText = findViewById(R.id.name_edit_text)
        val saveButton: ImageButton = findViewById(R.id.save_button)

        saveButton.setOnClickListener {
            val enteredName = nameEditText.text.toString()
            if (enteredName.isNotEmpty()) {
                saveUserName(enteredName)
                navigateToMainActivity()
            }
        }
    }

    private fun getUserName(): String? {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString("user_name", null)
    }

    private fun saveUserName(name: String) {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("user_name", name)
            apply()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun showGif() {
        val imageView:ImageView= findViewById(R.id.ae866)
        Glide.with(this).load(R.drawable.ae86).into(imageView)
    }
}
