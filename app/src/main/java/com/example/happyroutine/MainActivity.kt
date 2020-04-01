package com.example.happyroutine

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToLogIn(view: View) {
        val intent = Intent(this, log_in::class.java)
        startActivity(intent)
    }
    fun goToSignUp(view: View) {
        val intent = Intent(this, sign_up::class.java)
        startActivity(intent)
    }

}
