package com.example.happyroutine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class sign_up : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun goToLogIn(view: View) {
        val intent = Intent(this, log_in::class.java)
        startActivity(intent)
    }
}
